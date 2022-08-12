package com.recipepile.mappers;

import com.recipepile.domain.Category;
import com.recipepile.domain.dtos.CategorySlimDTO;
import com.recipepile.domain.dtos.CategoryThickDTO;
import com.recipepile.domain.dtos.post.CategoryPost;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface CategoryMapper {

    Category categoryPostToCategory(CategoryPost categoryPost);
    CategoryThickDTO categoryToCategoryThickDTO(Category category);

    CategorySlimDTO categoryToCategorySlimDTO(Category category);
    List<CategorySlimDTO> categoriesToCategoriesSlimDTO(List<Category> categories);
}
