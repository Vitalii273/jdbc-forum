package com.telran.jdbcforum.repository;

import com.telran.jdbcforum.entity.PostEntity;

import java.util.List;

public interface PostRepository {

    List<PostEntity> getAllPosts();

    PostEntity getPostById(int id);

    void addPost(PostEntity postEntity);

    void removePost(int id, String username);

}
