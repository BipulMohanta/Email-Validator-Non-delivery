package com.mav.email.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rest_api_logs")
public class RestLogs {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "requestReceivedOn")
	private Date requestReceivedOn;

	@Column(name = "requestURL")
	private String requestURL;

	@Column(name = "requestString")
	private String requestString;

	@Column(name = "responseStatus")
	private String responseStatus;

	@Column(name = "responseCode")
	private Integer responseCode;

	@Column(name = "responseSentOn")
	private Date responseSentOn;

	@Column(name = "responseString")
	private String responseString;

	public Date getRequestReceivedOn() {
		return requestReceivedOn;
	}

	public void setRequestReceivedOn(Date requestReceivedOn) {
		this.requestReceivedOn = requestReceivedOn;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public String getRequestString() {
		return requestString;
	}

	public void setRequestString(String requestString) {
		this.requestString = requestString;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public Date getResponseSentOn() {
		return responseSentOn;
	}

	public void setResponseSentOn(Date responseSentOn) {
		this.responseSentOn = responseSentOn;
	}

	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

}
