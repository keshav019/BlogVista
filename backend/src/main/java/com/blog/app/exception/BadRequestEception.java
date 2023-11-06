package com.blog.app.exception;

public class BadRequestEception extends Exception{
	private static final long serialVersionUID = 1L;
	public BadRequestEception(String message) {
		super(message);
	}
}
