package com.telran.jdbcforum.controller;

import com.telran.jdbcforum.dto.PostAddDto;
import com.telran.jdbcforum.dto.PostDto;
import com.telran.jdbcforum.entity.PostEntity;
import com.telran.jdbcforum.repository.PostRepository;
import com.telran.jdbcforum.repository.PostRepositoryImpl;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController {
    PostRepository repository;

    public PostController(PostRepositoryImpl repository) {
        this.repository = repository;
    }

    @PostMapping("posts/add")
    public void addPost(@RequestBody PostAddDto postAddDto) {
        repository.addPost(mapToEntity(postAddDto));
    }

    private PostEntity mapToEntity(PostAddDto postAddDto) {
        PostEntity postEntity = new PostEntity();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        postEntity.setContent(postAddDto.getContent());
        postEntity.setDateTime(timestamp);
        postEntity.setUserName(postAddDto.getUserName());
        return postEntity;
    }

    @GetMapping("posts")
    public List<PostDto> getPosts() {
        return repository.getAllPosts().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("posts/{id}")
    public PostDto getById(@PathVariable("id") int id) {
        return mapToDto(repository.getPostById(id));
    }

    private PostDto mapToDto(PostEntity postEntity) {
        PostDto dto = new PostDto();
        dto.id = postEntity.getId();
        dto.content = postEntity.getContent();
        dto.dateTime = postEntity.getDateTime().toLocalDateTime();
        dto.userName = postEntity.getUserName();
        return dto;
    }

    @DeleteMapping("posts/{postId}")
    public void removePostById(@PathVariable("postId") int topicId, String email){
        repository.removePost(topicId,email);
    }

}
