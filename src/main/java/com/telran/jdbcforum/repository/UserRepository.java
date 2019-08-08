package com.telran.jdbcforum.repository;


import com.telran.jdbcforum.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository {
    void addUser(UserEntity userEntity);

    UserEntity getUserByUsername(String username);

    void removeUser(String username);

    List<UserEntity> getAllUsers();

    UserDetails loadUserByUsername(String s);
}
