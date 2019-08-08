package com.telran.jdbcforum.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PostEntity {
    int id;
    String content;
    Timestamp dateTime;
    String userName;
}
