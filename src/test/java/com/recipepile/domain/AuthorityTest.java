package com.recipepile.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorityTest {

    Authority authority;

    @BeforeEach
    public void setUp(){
        authority = new Authority();
    }

    @Test
    void getAuthority() {
        assertEquals("ROLE_STANDARD-USER", authority.getAuthority());
    }
}