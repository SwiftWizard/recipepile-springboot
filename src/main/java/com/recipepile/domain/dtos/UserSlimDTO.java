package com.recipepile.domain.dtos;

import com.recipepile.domain.Authority;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserSlimDTO {
    private Long uuid;
    private String username;
    private String name;
    private String surname;
    private String nick;
    private String jwt;
    private Date jwtExpireTime;
    private Set<Authority> authorities;
}
