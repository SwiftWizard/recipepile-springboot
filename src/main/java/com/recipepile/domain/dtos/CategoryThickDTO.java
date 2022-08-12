package com.recipepile.domain.dtos;


import lombok.Data;

import java.util.List;

@Data
public class CategoryThickDTO {
    private Long categoryId;
    private String categoryName;
    private CategorySlimDTO parentCategory;
    private List<RecipeThickDTO> recipes;
}
