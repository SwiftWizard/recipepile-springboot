package com.recipepile.repositories;

import com.recipepile.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, Long> {
}
