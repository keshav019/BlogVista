package com.blog.app.service;

import com.blog.app.dto.LoginDto;
import com.blog.app.dto.LoginResponseDto;
import com.blog.app.dto.RegisterDto;
import com.blog.app.dto.RegisterResponseDto;
import com.blog.app.exception.TokenExpiredException;
import com.blog.app.exception.UserAlreadyExistException;
import com.blog.app.exception.UserNotExistException;


public interface UserService {
  public RegisterResponseDto register(RegisterDto registerDto) throws UserAlreadyExistException;
  public LoginResponseDto login(LoginDto loginDto) throws UserNotExistException;
  public String verifyEmail(String email,String token) throws UserNotExistException,TokenExpiredException;
  public RegisterResponseDto update(RegisterDto registerDto)throws UserNotExistException;
  public String forgotPassword(String email) throws UserNotExistException;
  public String resetPassword(String password,String email,String otp)throws UserNotExistException,TokenExpiredException;
}
