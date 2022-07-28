package com.recipepile.mappers;

import com.recipepile.domain.Category;
import com.recipepile.domain.dtos.CategoryThickDTO;
import com.recipepile.domain.dtos.post.CategoryPost;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface CategoryMapper {

    Category categoryPostToCategory(CategoryPost categoryPost);
    CategoryThickDTO categoryToCategoryThickDTO(Category category);
}
