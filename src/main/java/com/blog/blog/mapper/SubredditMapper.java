package com.blog.blog.mapper;

import com.blog.blog.Model.Post;
import com.blog.blog.Model.Subreddit;
import com.blog.blog.dto.SubredditDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface SubredditMapper {


    @Mapping(target="numberOfPosts",expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts)
    {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target="posts",ignore=true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}
