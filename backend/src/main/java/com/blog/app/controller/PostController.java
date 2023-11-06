package com.blog.app.controller;

import com.blog.app.exception.BadRequestEception;
import com.blog.app.exception.PostNotFoundException;
import com.blog.app.exception.UserNotExistException;
import com.blog.app.model.Post;
import com.blog.app.service.PostService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/post")
@CrossOrigin()
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping
	public ResponseEntity<Post> addPost(@RequestAttribute("email") String email, @RequestBody Post post) throws UserNotExistException {
		post = postService.addPost(email,post);
		return ResponseEntity.ok(post);
	}
	@PutMapping
	public ResponseEntity<Post> updatePost(@RequestAttribute("email") String email, @RequestBody Post post) throws UserNotExistException, PostNotFoundException, BadRequestEception {
		post = postService.updatePost(email,post);
		return ResponseEntity.ok(post);
	}
	@DeleteMapping("/{postId}")
	public ResponseEntity<String> deletePost(@RequestAttribute("email") String email, @PathVariable String postId) throws UserNotExistException, PostNotFoundException, BadRequestEception {
	postService.deletePost(email,postId);
	 return ResponseEntity.ok("{\"message\": \"" + "Post deleted\"}");
		
	}

	
	@GetMapping
	public ResponseEntity<List<Post>> getAllPosts() {
		List<Post> posts = postService.getAllPosts();
		return ResponseEntity.ok(posts);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Post> getPostById(@PathVariable String id) {
		Post post = postService.getPostById(id);
		return ResponseEntity.ok(post);
	}

	@GetMapping("/topics")
	public ResponseEntity<List<Post>> getPostsByTopics(@RequestBody List<String> topicIds) {
		List<Post> posts = postService.getPostsByTopics(topicIds);
		return ResponseEntity.ok(posts);
	}
}
