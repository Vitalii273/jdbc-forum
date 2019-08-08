package com.telran.jdbcforum.controller;

import com.telran.jdbcforum.dto.CommentDto;
import com.telran.jdbcforum.entity.CommentEntity;
import com.telran.jdbcforum.repository.CommentsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {
    CommentsRepository repository;

    public CommentController(CommentsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("comments")
    public List<CommentDto> getComments() {
        return repository.getAllComments().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("comments/{id}")
    public CommentDto getById(@PathVariable("id") int id) {
        return toDto(repository.getCommentById(id));
    }


    private CommentDto toDto(CommentEntity commentEntity) {
        CommentDto dto = new CommentDto();
        dto.id = commentEntity.getId();
        dto.comment = commentEntity.getComment();
        dto.dateTime = commentEntity.getDateTime().toLocalDateTime();
        dto.postId = commentEntity.getPostId();
        dto.username = commentEntity.getUserName();
        return dto;
    }

}
