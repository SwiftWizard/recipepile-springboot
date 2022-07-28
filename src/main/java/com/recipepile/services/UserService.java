package com.recipepile.services;

import com.recipepile.domain.User;
import com.recipepile.domain.VerificationToken;
import com.recipepile.exceptions.UsernameExistsException;

public interface UserService {
    User findByEmail(String email);
    User registerNewUserAccount(User user) throws UsernameExistsException;
    void createVerificationToken(User user, String token);
    VerificationToken getVerificationToken(String verificationToken);
    User saveRegisteredUser(User user);
}
