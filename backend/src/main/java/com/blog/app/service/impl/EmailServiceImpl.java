package com.blog.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.blog.app.dto.MailDto;
import com.blog.app.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	@Value("${spring.mail.username}")
	private String sender;
	
	@Autowired
	private  JavaMailSender mailSender;
	
	public void sendEmail(MailDto mailDto) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(sender);
		simpleMailMessage.setTo(mailDto.getEmail());
		simpleMailMessage.setSubject(mailDto.getSubject());
		simpleMailMessage.setText(mailDto.getEmailBody());
		mailSender.send(simpleMailMessage);	
	}
}
