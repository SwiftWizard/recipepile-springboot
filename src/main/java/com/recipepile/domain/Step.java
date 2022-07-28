package com.recipepile.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Step {

    private Integer stepNum;
    private String stepTitle;
    private String stepDesc;
    private String stepImg;

    private List<Ingredient> ingredients = new ArrayList<>();

}
