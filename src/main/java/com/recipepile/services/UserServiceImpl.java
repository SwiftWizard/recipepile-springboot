package com.recipepile.services;

import com.recipepile.domain.Authority;
import com.recipepile.domain.User;
import com.recipepile.domain.VerificationToken;
import com.recipepile.exceptions.UsernameExistsException;
import com.recipepile.repositories.UserRepository;
import com.recipepile.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired SequenceGeneratorService sequenceGeneratorService;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByUsername(email);
    }

    @Override
    public User registerNewUserAccount(User user) throws UsernameExistsException{

        System.out.println("\n\n\n      ->" + user.getUsername() + "\n\n\n");

        if(findByEmail(user.getUsername()) != null)
            throw new UsernameExistsException("User with the supplied email exists");

        user.setUuid(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        Authority standardAuthority = new Authority("ROLE_STANDARD-USER");
        user.addAuthority(standardAuthority);
        user.setAccountEnabled(false);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setAccountCredentialsNonExpired(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setLastPasswordReset(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(user, token);
        myToken.setTokenId(sequenceGeneratorService.generateSequence("verification-token_sequence"));
        verificationTokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken);
    }

    @Override
    public User saveRegisteredUser(User user) {
        return userRepository.save(user);
    }
}
