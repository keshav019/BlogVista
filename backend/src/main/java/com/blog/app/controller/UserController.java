package com.blog.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.dto.LoginDto;
import com.blog.app.dto.LoginResponseDto;
import com.blog.app.dto.RegisterDto;
import com.blog.app.dto.RegisterResponseDto;
import com.blog.app.dto.ResetPasswordDto;
import com.blog.app.exception.TokenExpiredException;
import com.blog.app.exception.UserAlreadyExistException;
import com.blog.app.exception.UserNotExistException;
import com.blog.app.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin()
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDto> signUp(@RequestBody @Valid RegisterDto registerDto) throws UserAlreadyExistException {
		RegisterResponseDto signupResponseDto = userService.register(registerDto);
		return ResponseEntity.ok(signupResponseDto);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginDto) throws UserNotExistException {
		LoginResponseDto loginResponseDto = userService.login(loginDto);
		return ResponseEntity.ok(loginResponseDto);
	}
	
	@GetMapping("/verify")
	public ResponseEntity<String> verifyEmail(
            @RequestParam String email,
            @RequestParam String otp
			) throws UserNotExistException, TokenExpiredException{
		System.out.println(otp);
		String res=userService.verifyEmail(email, otp);
		return ResponseEntity.ok("{\"message\": \"" + res + "\"}");
	}
	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody @Valid RegisterDto registerDto) throws UserNotExistException{
		RegisterResponseDto res=userService.update(registerDto);
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/forgot")
	public ResponseEntity<?> forgotPassword(@RequestParam String email) throws UserNotExistException{
		String res=userService.forgotPassword(email);
		return ResponseEntity.ok("{\"message\": \"" + res + "\"}");
	}
	@PostMapping("/reset")
	public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordDto resetPasswordDto) throws UserNotExistException,TokenExpiredException{
		String res=userService.resetPassword(resetPasswordDto.getPassword(), resetPasswordDto.getEmail(), resetPasswordDto.getOtp());
		return ResponseEntity.ok("{\"message\": \"" + res + "\"}");
	}

}
