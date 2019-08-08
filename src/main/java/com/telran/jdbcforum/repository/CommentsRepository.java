package com.telran.jdbcforum.repository;

import com.telran.jdbcforum.entity.CommentEntity;

import java.util.List;

public interface CommentsRepository {

    List<CommentEntity> getAllComments();

    CommentEntity getCommentById(int id);

    void addComment(int postId, String email);

    void removeComment(int postId, int commentId, String email);

    void updateComment(int postId, CommentEntity commentEntity);
}
