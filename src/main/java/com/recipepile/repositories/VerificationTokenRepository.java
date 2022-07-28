package com.recipepile.repositories;

import com.recipepile.domain.VerificationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken, Long> {

    VerificationToken findByToken(String verificationToken);
}
