package com.mav.email.exception;

import org.springframework.http.HttpStatus;

public class CustomServiceException extends Exception {

	private static final long serialVersionUID = -7282726297394426934L;
	
	private final HttpStatus httStatus;

	public CustomServiceException(HttpStatus httStatus) {
		super();
		this.httStatus = httStatus;
	}

	public CustomServiceException(String message, HttpStatus httStatus) {
		super(message);
		this.httStatus = httStatus;
	}

	public CustomServiceException(String message, Exception exception, HttpStatus httStatus) {
		super(message, exception);
		this.httStatus = httStatus;
	}

	public CustomServiceException(Exception exception, HttpStatus httStatus) {
		super(exception);
		this.httStatus = httStatus;
	}

	public HttpStatus getHttStatus() {
		return httStatus;
	}

}
