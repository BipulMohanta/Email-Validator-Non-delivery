package com.mav.email.exception;
/**
 * 
 * @author bipul.mohanta
 *
 */
public class ValidationException extends CustomRuntimeException {

	private static final long serialVersionUID = -8051738312371517835L;

	private String errorMessage;
	private Integer httpErrorCode;

	public ValidationException(String errorMessage) {
		super(errorMessage);
	}

	public ValidationException(String errorMessage, Exception e) {
		super(errorMessage, e);
		this.errorMessage = errorMessage;
	}
	public ValidationException(String errorMessage, Integer httpErrorCode) {
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.httpErrorCode = httpErrorCode;
	}
	public ValidationException(String errorMessage, Integer httpErrorCode, Exception e) {
		super(errorMessage, e);
		this.errorMessage = errorMessage;
		this.httpErrorCode = httpErrorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getHttpErrorCode() {
		return httpErrorCode;
	}

	public void setHttpErrorCode(Integer httpErrorCode) {
		this.httpErrorCode = httpErrorCode;
	}
}
