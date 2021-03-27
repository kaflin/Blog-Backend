package com.blog.blog.repository;

import com.blog.blog.Model.Post;
import com.blog.blog.Model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends  JpaRepository<Post,Long> {

    List<Post> findPostBySubreddit(Subreddit subreddit);
}
