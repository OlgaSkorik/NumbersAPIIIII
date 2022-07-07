package com.example.numbers.service;

import com.example.numbers.configuration.jwt.GenerateJWTUser;
import com.example.numbers.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JWTUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userService.findByUsername(username).isPresent()) {
            User user = userService.findByUsername(username).get();
            return GenerateJWTUser.create(user);
        } else {
            throw new UsernameNotFoundException("User with username: " + username + "not found");
        }
    }
}