package com.mav.email.exception;

/**
 * 
 * @author bipul.mohanta
 *
 */
public class CustomRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 9120903084796394493L;

	private String errorMessage;
	private Integer httpErrorCode;

	public CustomRuntimeException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public CustomRuntimeException(String errorMessage, Exception e) {
		super(errorMessage, e);
		this.errorMessage = errorMessage;
	}
	public CustomRuntimeException(String errorMessage, Integer httpErrorCode) {
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.httpErrorCode = httpErrorCode;
	}
	public CustomRuntimeException(String errorMessage, Integer httpErrorCode, Exception e) {
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
