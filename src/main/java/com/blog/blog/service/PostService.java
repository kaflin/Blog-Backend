//package com.blog.blog.service;
//
//import com.blog.blog.Model.Subreddit;
//import com.blog.blog.dto.PostRequest;
//import com.blog.blog.exceptions.SubredditNotFoundException;
//import com.blog.blog.mapper.PostMapper;
//import com.blog.blog.repository.PostRepository;
//import com.blog.blog.repository.SubredditRepository;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
//@Service
//@AllArgsConstructor
//@Transactional
//@Slf4j
//public class PostService {
//
//    private final PostRepository postRepository;
//    private final PostMapper postMapper;
//    private final SubredditRepository subredditRepository;
//    private final AuthService authService;
//
//
//    public void save(PostRequest postRequest) {
//        Subreddit subreddit= subredditRepository.findByName(postRequest.getSubredditName())
//                .orElseThrow(()->new SubredditNotFoundException(postRequest.getSubredditName()));
//        postRepository.save(postMapper.map(postRequest,subreddit));
//    }
//}
