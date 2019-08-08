package com.telran.jdbcforum.dto;

import java.time.LocalDateTime;

public class CommentDto {
    public int id;
    public String comment;
    public LocalDateTime dateTime;
    public int postId;
    public String username;
}
