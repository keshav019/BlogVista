package com.blog.app.service;

import java.util.List;

import com.blog.app.exception.BadRequestEception;
import com.blog.app.exception.PostNotFoundException;
import com.blog.app.exception.UserNotExistException;
import com.blog.app.model.Post;

public interface PostService {
	public Post addPost(String email,Post post) throws UserNotExistException;
	public Post updatePost(String email,Post post) throws UserNotExistException ,PostNotFoundException,BadRequestEception;
	public void deletePost( String email,String postId) throws UserNotExistException ,PostNotFoundException,BadRequestEception;

    List<Post> getAllPosts();

    Post getPostById(String id);
    
    public List<Post> getPostsByTopics(List<String> topicNames);
    
}
