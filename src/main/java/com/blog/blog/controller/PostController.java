package com.blog.blog.controller;


import com.blog.blog.dto.PostRequest;
import com.blog.blog.dto.PostResponse;
import com.blog.blog.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping("/save")
    public ResponseEntity<Void> createPosts(@RequestBody PostRequest postRequest)
    {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPost()
    {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPost());
    }
    @GetMapping("/postBySubreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostBySubreddit(@PathVariable Long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostBySubreddit(id));
    }

}
