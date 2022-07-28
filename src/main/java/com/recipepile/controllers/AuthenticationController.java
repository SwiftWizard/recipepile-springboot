package com.recipepile.controllers;

import com.recipepile.applicationEvents.OnRegistrationCompleteEvent;
import com.recipepile.domain.AuthenticationRequest;
import com.recipepile.domain.User;
import com.recipepile.domain.VerificationToken;
import com.recipepile.domain.dtos.UserSlimDTO;
import com.recipepile.domain.dtos.UserSlimDTOWithMessages;
import com.recipepile.domain.dtos.post.UserPost;
import com.recipepile.exceptions.UsernameExistsException;
import com.recipepile.mappers.UserMapper;
import com.recipepile.services.UserService;
import com.recipepile.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserSlimDTOWithMessages> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        System.out.println("About to try authentication with credentials: " + authenticationRequest);
        List<String> messages = new ArrayList<>();
        UserSlimDTO user = null;
        try {
            System.out.println(" -> Trying authentication...");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e) {
            System.out.println("        -> Authentication failed");
            messages.add("Authentication failed, check username and password");
            return new ResponseEntity<UserSlimDTOWithMessages>(new UserSlimDTOWithMessages(user, messages), HttpStatus.UNAUTHORIZED);
        }
        catch(LockedException e){
            System.out.println("        ->Account is locked");
            messages.add("Account is locked");
            return new ResponseEntity<UserSlimDTOWithMessages>(new UserSlimDTOWithMessages(user, messages), HttpStatus.UNAUTHORIZED);
        }
        catch(DisabledException e){
            System.out.println("        ->Account is disabled");
            messages.add("Account is disabled");
            return new ResponseEntity<UserSlimDTOWithMessages>(new UserSlimDTOWithMessages(user, messages), HttpStatus.UNAUTHORIZED);
        }
        catch(CredentialsExpiredException e){
            System.out.println("        ->Account credentials expired");
            messages.add("Account credentials expired");
            return new ResponseEntity<UserSlimDTOWithMessages>(new UserSlimDTOWithMessages(user, messages), HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        messages.add("Login successful");
        UserSlimDTO slimUser = userMapper.userToUserSlimDTO(userService.findByEmail(userDetails.getUsername()));
        slimUser.setJwt(jwt);
        slimUser.setJwtExpireTime(jwtUtil.extractExpiration(jwt));

        return ResponseEntity.ok(new UserSlimDTOWithMessages(slimUser, messages));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserSlimDTOWithMessages> register(@RequestBody UserPost userPost, HttpServletRequest request) {
        List<String> messages = new ArrayList<>();

        UserSlimDTOWithMessages result;

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        User user = userMapper.userPostToUser(userPost);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                messages.add(violation.getMessage());
            }
            ResponseEntity<UserSlimDTOWithMessages> responseEntity;
            result = new UserSlimDTOWithMessages(null, messages);
            responseEntity = new ResponseEntity<UserSlimDTOWithMessages>(result, HttpStatus.CONFLICT);
            return responseEntity;

        } else {
            System.out.println(user);

            try {
                User newUser = userService.registerNewUserAccount(user);
                messages.add("Successfully registered");
                messages.add("Please visit your email inbox and verify your account");
                String appUrl = request.getContextPath() + "/api/auth";
                applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(newUser, request.getLocale(), appUrl));
                UserSlimDTO slimUserDTO = userMapper.userToUserSlimDTO(newUser);
                result = new UserSlimDTOWithMessages(slimUserDTO, messages);
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }catch (UsernameExistsException uEx){
                return new ResponseEntity<>(new UserSlimDTOWithMessages(null, Arrays.asList("Email already exists in the app")), HttpStatus.CONFLICT);
            }
        }
    }

    @GetMapping("/registrationConfirm")
    public ResponseEntity<UserSlimDTOWithMessages> confirmRegistration(WebRequest request, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            return new ResponseEntity<>(new UserSlimDTOWithMessages(null, Arrays.asList("No token provided")), HttpStatus.NO_CONTENT);
        }

        User user = verificationToken.getUser();
        LocalDateTime currentDateTime = LocalDateTime.now();

        if ((verificationToken.getExpiryTime().compareTo(currentDateTime) <= 0)) {
            return new ResponseEntity<>(new UserSlimDTOWithMessages(null, Arrays.asList("Verification link expired ")), HttpStatus.GONE);
        }

        user.setAccountEnabled(true);
        userService.saveRegisteredUser(user);

        return new ResponseEntity<>(new UserSlimDTOWithMessages(userMapper.userToUserSlimDTO(user), Arrays.asList("Account activated")), HttpStatus.OK);
    }
}
