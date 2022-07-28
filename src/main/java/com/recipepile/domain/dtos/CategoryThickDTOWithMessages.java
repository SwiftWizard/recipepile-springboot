package com.recipepile.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryThickDTOWithMessages {
    @JsonProperty("data")
    private CategoryThickDTO catagoryThickDTO;

    @JsonProperty("messages")
    private List<String> messages;

}
