package com.recipepile.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategorySlimDTO {
    private Long categoryId;
    private String categoryName;
    private CategorySlimDTO parentCategory;
}
