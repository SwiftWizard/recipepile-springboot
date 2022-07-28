package com.recipepile.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSlimDTOWithMessages {
    @JsonProperty("data")
    private UserSlimDTO user;

    @JsonProperty("messages")
    private List<String> messages;
}
