package com.recipepile.domain.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class UserSlimDTO {
    private Long uuid;
    private String username;
    private String name;
    private String surname;
    private String jwt;
    private Date jwtExpireTime;
}
