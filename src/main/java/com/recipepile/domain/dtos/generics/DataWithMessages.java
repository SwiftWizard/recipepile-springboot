package com.recipepile.domain.dtos.generics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataWithMessages <D, M>{
    @JsonProperty("data")
    private D data;
    @JsonProperty("messages")
    private M messages;
}
