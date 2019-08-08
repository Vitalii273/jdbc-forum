package com.telran.jdbcforum.controller;

import com.telran.jdbcforum.dto.UserDto;
import com.telran.jdbcforum.entity.UserEntity;
import com.telran.jdbcforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("account")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping
    public void addUser(@RequestBody UserDto userAddDto) {
        userRepository.addUser(mapToEntity(userAddDto));
    }

    private UserEntity mapToEntity(UserDto userAddDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userAddDto.getUsername());
        userEntity.setPassword(userAddDto.getPassword());
        return userEntity;
    }

    @GetMapping("all")
    public List<UserDto> getUsers() {
        return userRepository.getAllUsers().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private UserDto mapToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUsername(userEntity.getUsername());
        userDto.setPassword(userEntity.getPassword());
        return userDto;
    }

    @GetMapping("{username}")
    public UserDto getById(@PathVariable("username") String username) {
        return mapToDto(userRepository.getUserByUsername(username));
    }

    @DeleteMapping("{username}")
    public void removeUser(@PathVariable("username") String username) {
        userRepository.removeUser(username);
    }
}
