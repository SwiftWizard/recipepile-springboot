package com.recipepile.controllers;

import com.recipepile.domain.Category;
import com.recipepile.domain.dtos.CategorySlimDTO;
import com.recipepile.domain.dtos.CategoryThickDTO;
import com.recipepile.domain.dtos.generics.DataWithMessages;
import com.recipepile.domain.dtos.post.CategoryPost;
import com.recipepile.mappers.CategoryMapper;
import com.recipepile.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @PostMapping("manage/category/new")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    ResponseEntity<DataWithMessages<CategoryThickDTO, List<String>>> createCategory(@RequestBody CategoryPost categoryPost){
        Category category = categoryMapper.categoryPostToCategory(categoryPost);

        Category persistedCategory = categoryService.addCategory(category);

        CategoryThickDTO resultingCategoryThick = categoryMapper.categoryToCategoryThickDTO(persistedCategory);

        return new ResponseEntity<>(new DataWithMessages<>(resultingCategoryThick, Arrays.asList("Category created")), HttpStatus.CREATED);
    }

    @GetMapping("public/category/all")
    ResponseEntity<DataWithMessages<List<CategorySlimDTO>, List<String>>> listCategories(){
        List<CategorySlimDTO> result = categoryMapper.categoriesToCategoriesSlimDTO(categoryService.fetchCategories());
        return new ResponseEntity<>(new DataWithMessages<>(result, Arrays.asList("Successfully feetched categories")), HttpStatus.OK);
    }
}
