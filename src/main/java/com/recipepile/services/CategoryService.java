package com.recipepile.services;

import com.recipepile.domain.Category;

public interface CategoryService {
    Category addCategory(Category category);
    void removeCategory(Category category);

    boolean categoryExists(Category category);

}
