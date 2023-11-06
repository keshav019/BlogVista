
package com.blog.app.service.impl;

import com.blog.app.exception.BadRequestEception;
import com.blog.app.exception.PostNotFoundException;
import com.blog.app.exception.UserNotExistException;
import com.blog.app.model.Post;
import com.blog.app.model.User;
import com.blog.app.repository.PostRepository;
import com.blog.app.repository.UserRepository;
import com.blog.app.service.PostService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
    private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;

    @Override
    public Post addPost(String email,Post post) throws UserNotExistException {
    	User user = userRepository.findByEmail(email).orElse(null);
		if (user == null) {
			throw new UserNotExistException("User Not Exist with email: " + email);
		}
    	post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElse(null);
    }

	@Override
	public List<Post> getPostsByTopics(List<String> topicIds) {
		return postRepository.findByTopicsIn(topicIds);
	}

	@Override
	public Post updatePost(String email,Post post) throws UserNotExistException,PostNotFoundException,BadRequestEception{
		User user = userRepository.findByEmail(email).orElse(null);
		if (user == null) {
			throw new UserNotExistException("User Not Exist with email: " + email);
		}
		Post existingPost = postRepository.findById(post.getPostId()).orElse(null);
		if (existingPost == null) {
			throw new PostNotFoundException("Post Not Exist!");
		}
		if (!existingPost.getUser().getUserId().equals(user.getUserId())) {
			throw new BadRequestEception("Unauthorized to update post!");
		}

		return postRepository.save(post);
		
	}

	@Override
	public void deletePost(String email,String postId) throws UserNotExistException ,PostNotFoundException,BadRequestEception{
		User user = userRepository.findByEmail(email).orElse(null);
		if (user == null) {
			throw new UserNotExistException("User Not Exist with email: " + email);
		}
		Post existingPost = postRepository.findById(postId).orElse(null);
		if (existingPost == null) {
			throw new PostNotFoundException("Post Not Exist!");
		}
		if (!existingPost.getUser().getUserId().equals(user.getUserId())) {
			throw new BadRequestEception("Unauthorized to delete post!");
		}
		postRepository.deleteById(postId);
	}
}
