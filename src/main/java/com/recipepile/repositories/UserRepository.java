package com.recipepile.repositories;

import com.recipepile.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByUsername(String username);
}
