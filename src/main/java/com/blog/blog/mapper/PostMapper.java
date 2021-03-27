package com.blog.blog.mapper;


import com.blog.blog.Model.Post;
import com.blog.blog.Model.Subreddit;
import com.blog.blog.Model.User;
import com.blog.blog.dto.PostRequest;
import com.blog.blog.dto.PostResponse;
import com.blog.blog.dto.PostResponse1;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface  PostMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target="id",source="post.postId")
    @Mapping(target="postName",source="post.postName")
    @Mapping(target="description",source="post.description")
    @Mapping(target="url",source="post.url")
    @Mapping(target="subredditName",source="post.subreddit.name")
    @Mapping(target="userName",source="post.user.username")
    PostResponse mapToDto(Post post);


//    PostResponse mapToDto(Post post);
}
