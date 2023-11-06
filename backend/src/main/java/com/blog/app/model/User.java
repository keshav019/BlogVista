package com.blog.app.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.blog.app.dto.RegisterDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
	@Id
	private String userId;
	@Indexed(unique = true)
	private String email;
	private String firstname;
	private String lastname;
	private String password;
	private boolean enable;
	private String token;
	private LocalDateTime createdTimeStamp;
	private LocalDateTime updateTimeStamp;
	@DBRef
	private List<Topic> intrestTopics = new ArrayList<>();
	@DBRef(lazy = true)
	private List<Post> savedPosts = new ArrayList<>();

	public User(RegisterDto registerDto) {
		this.email = registerDto.getEmail();
		this.firstname = registerDto.getFirstname();
		this.lastname = registerDto.getLastname();
		this.password = registerDto.getPassword();
	}

	public void updateUser(RegisterDto registerDto) {
		this.firstname = registerDto.getFirstname();
		this.lastname = registerDto.getLastname();
	}

	public boolean isTokenExpired() {
		LocalDateTime currentTime = LocalDateTime.now();
		long minutesDifference = ChronoUnit.MINUTES.between(updateTimeStamp, currentTime);
		return minutesDifference >= 10;
	}

	public boolean verified() {
		return this.enable;
	}

}
