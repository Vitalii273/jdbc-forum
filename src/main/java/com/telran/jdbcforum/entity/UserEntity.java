package com.telran.jdbcforum.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter
public class UserEntity {
    private String username;
    private String password;
    private String[] authorities;
}
