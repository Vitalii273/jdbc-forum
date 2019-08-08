package com.telran.jdbcforum.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ForumServiceImpl implements ForumService {


    @Override
    public boolean isValidEmail(String email) {
        try {
            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()){
                return true;
            }
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, e.getMessage());
        }
        return false;

    }
}
