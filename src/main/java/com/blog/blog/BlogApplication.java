package com.blog.blog;

import com.blog.blog.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class BlogApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(com.blog.blog.BlogApplication.class, args);
	}


}
