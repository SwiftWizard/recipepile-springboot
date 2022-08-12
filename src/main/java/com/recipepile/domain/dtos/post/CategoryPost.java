package com.recipepile.domain.dtos.post;

import com.recipepile.domain.dtos.CategorySlimDTO;
import lombok.Data;

@Data
public class CategoryPost {
    private String categoryName;
    private CategorySlimDTO parentCategory;
}
