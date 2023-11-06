package com.blog.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.app.service.CloudinaryService;



@RestController
@RequestMapping("/api/v1/file")
@CrossOrigin()
public class ImageUploadController {
	
	@Autowired
	private CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestBody MultipartFile upload) throws IOException {
        String url=cloudinaryService.uploadImage(upload);
        return ResponseEntity.ok("{\"url\":\"" + url + "\"}");
    }
}

