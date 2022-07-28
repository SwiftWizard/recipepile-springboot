package com.recipepile.domain;

import java.util.Date;

public class AuthenticationResponse {

    private final String jwt;

    private final Date expiresIn;

    public AuthenticationResponse(String jwt, Date expiresIn) {
        this.jwt = jwt;
        this.expiresIn = expiresIn;
    }

    public String getJwt() {
        return jwt;
    }

    public Date getExpiresIn(){return expiresIn;}
}