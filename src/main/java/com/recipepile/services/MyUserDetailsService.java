package com.recipepile.services;


import com.recipepile.domain.User;
import com.recipepile.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        try {
            User user = userRepository.findByUsername(email);
            if (user == null) {
                throw new UsernameNotFoundException(
                        "No user found with username: " + email);
            }

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.isEnabled(),
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    user.getAuthorities());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
