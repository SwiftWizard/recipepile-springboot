package com.recipepile.services;

import com.recipepile.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category addCategory(Category category);
    void removeCategory(Category category);
    boolean categoryExists(Category category);

    List<Category> fetchCategories();

    Optional<Category> findById(Long id);
}
