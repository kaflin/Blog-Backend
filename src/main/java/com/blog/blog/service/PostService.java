package com.blog.blog.service;


import com.blog.blog.Model.Post;
import com.blog.blog.Model.Subreddit;
import com.blog.blog.Model.User;
import com.blog.blog.dto.PostRequest;
import com.blog.blog.dto.PostResponse;
import com.blog.blog.exceptions.PostNotFoundException;
import com.blog.blog.exceptions.SubredditNotFoundException;
import com.blog.blog.mapper.PostMapper;
import com.blog.blog.repository.PostRepository;
import com.blog.blog.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final PostMapper postMapper;
    private final AuthService authService;

    @Transactional
    public Post save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        User currentUser = authService.getCurrentUser();
        return postMapper.map(postRequest, subreddit, currentUser);
    }

    @Transactional
    public PostResponse getPostById(Long id) {
        Post post=postRepository.findById(id)
                .orElseThrow(()->new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);

    }


    public List<PostResponse> getAllPost() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());

    }

    public List<PostResponse> getPostBySubreddit(Long id) {
        Subreddit subreddit=subredditRepository.findById(id)
                .orElseThrow(()->new SubredditNotFoundException(id.toString()));
        List<Post> posts =postRepository.findPostBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());

    }
}
