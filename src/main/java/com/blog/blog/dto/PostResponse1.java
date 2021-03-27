package com.blog.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse1 {

    private String postName;
    private String userName;
    private String subredditName;

}
