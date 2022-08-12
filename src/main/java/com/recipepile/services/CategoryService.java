package com.recipepile.services;

import com.recipepile.domain.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory(Category category);
    void removeCategory(Category category);
    boolean categoryExists(Category category);

    List<Category> fetchCategories();
}
