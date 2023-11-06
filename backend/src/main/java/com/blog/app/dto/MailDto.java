package com.blog.app.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MailDto {
	private String email;
	private String subject;
	private String emailBody;
	
	public void otpMail(String email,String otp,String name) {
		this.email=email;
		this.subject="Email verification";
		this.emailBody="Hello "+name +"\n"+ "Otp to verify your Email is : "+ otp;
		
	}
	public void registrationMail(String email,String name) {
		this.email=email;
		this.subject="Registration";
		this.emailBody="Hello "+name +"\n"+ "Your Accout is created sucessfully : ";
	}
}
