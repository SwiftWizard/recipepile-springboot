package com.recipepile.domain;

import lombok.Data;

@Data
public class Ingredient {
    private String ingredientName;
    private String description;
    private double amount;
    private UnitOfMeasure unitOfMeasure;
}
