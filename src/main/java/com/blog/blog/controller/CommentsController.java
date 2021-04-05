package com.blog.blog.controller;

import com.blog.blog.dto.CommentsDto;
import com.blog.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

    private final CommentService commentService;
    @PostMapping("/save")
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto)
    {
        commentService.save(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @GetMapping("/by-user/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId)
    {
       return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping
    public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@RequestParam(value="userName") String userName)
    {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(userName));

    }



}
