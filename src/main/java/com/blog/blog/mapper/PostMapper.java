package com.blog.blog.mapper;

import com.blog.blog.Model.Post;
import com.blog.blog.Model.Subreddit;
import com.blog.blog.dto.PostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Mapper(componentModel="spring")
@Component
public abstract class PostMapper {


    @Mapping(target="description",source="postRequest.description")
    @Mapping(target = "subreddit",source="subreddit")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit);
}
