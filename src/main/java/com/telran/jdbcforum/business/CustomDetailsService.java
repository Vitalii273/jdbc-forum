package com.telran.jdbcforum.business;

import com.telran.jdbcforum.entity.UserEntity;
import com.telran.jdbcforum.repository.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class CustomDetailsService implements UserDetailsService {

    private UserRepository repository;

    public CustomDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository.loadUserByUsername(username);
    }
}
