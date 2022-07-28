package com.recipepile.services;

import com.recipepile.domain.Category;
import com.recipepile.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public Category addCategory(Category category) {
        category.setCategoryId(sequenceGeneratorService.generateSequence("categories_sequence"));
        return categoryRepository.save(category);
    }

    @Override
    public void removeCategory(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public boolean categoryExists(Category category) {
        return categoryRepository.existsById(category.getCategoryId());
    }
}
