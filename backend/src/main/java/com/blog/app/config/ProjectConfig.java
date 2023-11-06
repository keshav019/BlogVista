package com.blog.app.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;


@Configuration
public class ProjectConfig {

	@Bean
	 Cloudinary getCloudinary() {
		Map<String,String> config=new HashMap<>();
		config.put("cloud_name", "dhgn4khyo");
		config.put("api_key", "367331879549147");
		config.put("api_secret", "yC9uo-Noj6TB_zbuLbiTyoVCE-o");
		config.put("secure", "true");
		return new Cloudinary(config);
	}
}
