package com.blog.app.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.app.dto.LoginDto;
import com.blog.app.dto.LoginResponseDto;
import com.blog.app.dto.MailDto;
import com.blog.app.dto.RegisterDto;
import com.blog.app.dto.RegisterResponseDto;
import com.blog.app.exception.TokenExpiredException;
import com.blog.app.exception.UserAlreadyExistException;
import com.blog.app.exception.UserNotExistException;
import com.blog.app.model.User;
import com.blog.app.model.UserDetailsImpl;
import com.blog.app.repository.UserRepository;
import com.blog.app.service.EmailService;
import com.blog.app.service.JwtService;
import com.blog.app.service.UserService;
import com.blog.app.utils.IdGenerator;
import com.blog.app.utils.OTPGenerator;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private EmailService emailService;


	@Override
	public RegisterResponseDto register(RegisterDto registerDto) throws UserAlreadyExistException {
		User existingUser = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
		if (existingUser == null) {
			registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
			String userId=IdGenerator.GetUniqueId();
			User user = new User(registerDto);
			user.setUserId(userId);
			user.setEnable(false);
			user.setCreatedTimeStamp(LocalDateTime.now());
			user.setUpdateTimeStamp(LocalDateTime.now());
			String otp = OTPGenerator.generateOTP();
			user.setToken(otp);
			user = userRepository.save(user);
			MailDto mail = new MailDto();
			mail.otpMail(user.getEmail(), otp, user.getFirstname());
			emailService.sendEmail(mail);
			return new RegisterResponseDto(user);

		} else if (!existingUser.verified()) {
			existingUser.setUpdateTimeStamp(LocalDateTime.now());
			existingUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
			existingUser.setFirstname(registerDto.getFirstname());
			existingUser.setLastname(registerDto.getLastname());
			String otp = OTPGenerator.generateOTP();
			existingUser.setToken(otp);
			userRepository.save(existingUser);
			MailDto mail = new MailDto();
			mail.otpMail(existingUser.getEmail(), otp, existingUser.getFirstname());
			emailService.sendEmail(mail);
			return new RegisterResponseDto(existingUser);
		} else {
			throw new UserAlreadyExistException("Email already Exist");
		}

	}

	@Override
	public LoginResponseDto login(LoginDto loginDto) throws UserNotExistException {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		UserDetailsImpl user =(UserDetailsImpl) authentication.getPrincipal();
		String jwtToken = jwtService.generateToken(user);
		LoginResponseDto loginResponse = new LoginResponseDto();
		loginResponse.setEmail(loginDto.getEmail());
		loginResponse.setUserId(user.getUserId());
		loginResponse.setFirstname(user.getFirstname());
		loginResponse.setLastname(user.getLastname());
		loginResponse.setToken(jwtToken);
		return loginResponse;
	}

	@Override
	public String verifyEmail(String email, String token) throws UserNotExistException, TokenExpiredException {
		User user = userRepository.findByEmail(email).orElse(null);
		if (user == null) {
			throw new UserNotExistException("User Not Exist with email: " + email);
		}
		if (user.isTokenExpired()) {
			throw new TokenExpiredException("OTP Expired !");
		}
		if (user.getToken() == null || !user.getToken().equals(token)) {
			throw new TokenExpiredException("Please Enter correct OTP !");
		}
		user.setEnable(true);
		user.setToken(null);
		user = userRepository.save(user);
		MailDto mail = new MailDto();
		mail.registrationMail(user.getEmail(), user.getFirstname());
		emailService.sendEmail(mail);
		return "Email verified !";
	}

	@Override
	public RegisterResponseDto update(RegisterDto registerDto) throws UserNotExistException {
		User user = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
		if (user == null) {
			throw new UserNotExistException("User Not Exist with email: " + registerDto.getEmail());
		}
		user.updateUser(registerDto);
		user = userRepository.save(user);
		return new RegisterResponseDto(user);
	}

	@Override
	public String forgotPassword(String email) throws UserNotExistException {
		User user = userRepository.findByEmail(email).orElse(null);
		if (user == null) {
			throw new UserNotExistException("User Not Exist with email: " + email);
		}
		String otp = OTPGenerator.generateOTP();
		user.setToken(otp);
		user.setUpdateTimeStamp(LocalDateTime.now());
		user = userRepository.save(user);
		MailDto mail = new MailDto();
		mail.otpMail(user.getEmail(), otp, user.getFirstname());
		emailService.sendEmail(mail);
		return "Otp Email Sent !";
	}

	@Override
	public String resetPassword(String password, String email, String otp) throws UserNotExistException, TokenExpiredException {
		User user = userRepository.findByEmail(email).orElse(null);
		if (user == null) {
			throw new UserNotExistException("User Not Exist with email: " + email);
		}
		if (user.isTokenExpired()) {
			throw new TokenExpiredException("OTP Expired !");
		}
		if (user.getToken() == null || !user.getToken().equals(otp)) {
			throw new TokenExpiredException("Please Enter correct OTP !");
		}
		user.setEnable(true);
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
		return "Password Updated";
	}

}
