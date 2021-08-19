package com.mav.email.dto;

import java.util.List;

public class ResponseDTO {

	private Boolean success;
	private Integer code;
	private List<String> message;
	private Object response;

	public ResponseDTO() {

	}

	public ResponseDTO(Boolean success, Integer code, List<String> message, Object response) {
		this.success = success;
		this.code = code;
		this.message = message;
		this.response = response;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

}
