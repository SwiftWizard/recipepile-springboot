package com.recipepile.applicationListners;

import com.recipepile.applicationEvents.OnRegistrationCompleteEvent;
import com.recipepile.domain.User;
import com.recipepile.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    @Qualifier("emailsender")
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = "Welcome to recipepile.online, enjoy cooking and sharing new recipes!";


        System.out.println("\n\n"+sender+"\n\n");
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(sender);
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message  + "https://recipepile.azurewebsites.net" + confirmationUrl);
        mailSender.send(email);
    }
}
