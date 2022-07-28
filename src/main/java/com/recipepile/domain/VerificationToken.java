package com.recipepile.domain;

import com.recipepile.repositories.VerificationTokenRepository;
import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class VerificationToken {

    @Transient
    private static final int EXPIRATION = 60*24;

    @Transient
    public static final String SEQUENCE_NAME = "verification-token_sequence";

    @Id
    private Long tokenId;

    private String token;

    @DBRef
    private User user;

    private LocalDateTime expiryTime;

    public VerificationToken(User user, String token){
        this.user = user;
        this.token = token;
        this.expiryTime = calculateExpiryTime(EXPIRATION);
    }

    private LocalDateTime calculateExpiryTime(int expiryTimeInMinutes){
        return LocalDateTime.now().plusMinutes(expiryTimeInMinutes);
    }
}
