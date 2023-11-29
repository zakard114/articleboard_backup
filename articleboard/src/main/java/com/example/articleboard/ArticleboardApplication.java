package com.example.articleboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ArticleboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleboardApplication.class, args);
	}

}
