package com.recipepile.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecipeThickDTOWithMessages {
    @JsonProperty("data")
    private RecipeThickDTO recipeThickDTO;

    @JsonProperty("messages")
    private List<String> messages;
}
