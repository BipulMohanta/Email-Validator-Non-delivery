package com.mav.email.exception;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author bipul.mohanta
 *
 */
public class ValidationException extends CustomServiceException {

	private static final long serialVersionUID = -8051738312371517835L;

	private final String errorMessage;
	private final HttpStatus httpStatus;

	public ValidationException(HttpStatus httpStatus, String errorMessage) {
		super(errorMessage, httpStatus);
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public HttpStatus gethttpStatus() {
		return httpStatus;
	}

}
