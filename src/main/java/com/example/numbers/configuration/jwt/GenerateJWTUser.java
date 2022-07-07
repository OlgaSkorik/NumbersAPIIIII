package com.example.numbers.configuration.jwt;


import com.example.numbers.entity.Role;
import com.example.numbers.entity.Status;
import com.example.numbers.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateJWTUser {

    public static JWTUser create(User user){
        return new JWTUser(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoleList()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getTypeOfRole()))
                .collect(Collectors.toList());
    }
}