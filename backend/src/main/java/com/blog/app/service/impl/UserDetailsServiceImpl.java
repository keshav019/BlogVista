package com.blog.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.app.model.User;
import com.blog.app.model.UserDetailsImpl;
import com.blog.app.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found with email : "+username));
		if(user.verified()==false) {
			System.out.println(user.getFirstname());
			throw new UsernameNotFoundException("Email Not Verified!: ");
		}
		UserDetailsImpl userDetailsImpl=new UserDetailsImpl();
		userDetailsImpl.setUsername(username);
		userDetailsImpl.setUserId(user.getUserId());
		userDetailsImpl.setPassword(user.getPassword());
		userDetailsImpl.setFirstname(user.getFirstname());
		userDetailsImpl.setLastname(user.getLastname());
		return userDetailsImpl;
	}

}
