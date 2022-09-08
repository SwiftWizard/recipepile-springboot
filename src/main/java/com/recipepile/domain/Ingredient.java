package com.recipepile.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Ingredient {
    private String ingredientName;
    private double amount;
    private String unitOfMeasure;
}
