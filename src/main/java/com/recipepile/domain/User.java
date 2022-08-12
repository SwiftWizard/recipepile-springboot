package com.recipepile.domain;

import com.recipepile.validationConstraints.HumanUserValidation;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Document
@Data
public class User implements UserDetails, HumanUserValidation {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private Long uuid;

    @NotNull(message = "Name cannot be null!")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private String surname;
    private String username;

    @NotNull(message = "Nickname cannot be null!")
    @NotEmpty(message = "Nickname cannot be empty")
    private String nick;
    @NotNull(message = "You must enter a password, it cannot be null!")
    @NotEmpty(message = "The password cannot be empty!")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;


    private Set<Authority> authorities = new HashSet<>();

    private LocalDateTime lastPasswordReset;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean accountEnabled;
    private boolean accountCredentialsNonExpired;

    public User addAuthority(Authority authority){
        this.authorities.add(authority);
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword(){return password; }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return accountCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return accountEnabled;
    }

    @Override
    public String getEmail() {
        return this.username;
    }
}
