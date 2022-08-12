package com.recipepile.mappers;

import com.recipepile.domain.Authority;
import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Mapper(componentModel="spring")
public interface AuthorityMapper {
    Authority grantedAuthorityToAuthority(GrantedAuthority grantedAuthority);
    Set<Authority> grantedAuthoritiesToAuthorities(Set<GrantedAuthority> grantedAuthorities);
}
