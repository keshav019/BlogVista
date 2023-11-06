package com.blog.app.repository;

import com.blog.app.model.Post;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
	List<Post> findByTopicsIn(List<String> topicIds);
}

