package com.recipepile.controllers;

import com.recipepile.domain.Category;
import com.recipepile.domain.dtos.CategoryThickDTO;
import com.recipepile.domain.dtos.CategoryThickDTOWithMessages;
import com.recipepile.domain.dtos.post.CategoryPost;
import com.recipepile.mappers.CategoryMapper;
import com.recipepile.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("api/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @PostMapping("manage/category/new")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    ResponseEntity<CategoryThickDTOWithMessages> createCategory(@RequestBody CategoryPost categoryPost){
        Category category = categoryMapper.categoryPostToCategory(categoryPost);

        Category persistedCategory = categoryService.addCategory(category);

        CategoryThickDTO resultingCategoryThick = categoryMapper.categoryToCategoryThickDTO(persistedCategory);

        return new ResponseEntity<>(new CategoryThickDTOWithMessages(resultingCategoryThick, Arrays.asList("Category created")), HttpStatus.CREATED);
    }
}
