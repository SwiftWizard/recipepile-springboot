package com.recipepile.domain.dtos;

import lombok.Data;

@Data
public class UserSlimInfoDTO {
    private Long uuid;
    private String username;
    private String name;
    private String surname;
}
