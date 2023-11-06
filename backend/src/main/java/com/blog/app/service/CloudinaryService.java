package com.blog.app.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public interface CloudinaryService {
	 public String uploadImage(MultipartFile file) throws IOException;
}
