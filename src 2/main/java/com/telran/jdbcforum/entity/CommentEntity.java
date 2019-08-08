package com.telran.jdbcforum.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CommentEntity {
    int id;
    String comment;
    Timestamp dateTime;
    int postId;
    String userName;

}
