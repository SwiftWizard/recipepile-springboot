package com.recipepile.domain;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
    private String authority;

    public Authority() {
        this.authority = "ROLE_STANDARD-USER";
    }

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}