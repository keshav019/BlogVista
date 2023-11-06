package com.blog.app.service;



import com.blog.app.dto.MailDto;

public interface EmailService {
	public void sendEmail(MailDto mailDto);
}
