package com.mav.email.bo;

import java.util.List;

/**
 * 
 * @author bipul.mohanta
 *
 */
public class EmailMessage {

	private String fromUser;
	private String subject;
	private String bodyMessage;
	private List<String> toEmail;
	private List<String> ccEmail;
	private List<String> bccEmail;
	private String customMessageHeaderId;
	private List<String> serverFileIds;
	private String messageId;
	private boolean isAuthReq;
	private List<Attachment> attachments;

	public EmailMessage() {

	}

	public EmailMessage(String fromUser, String subject, String bodyMessage, List<String> toEmail, List<String> ccEmail,
			List<String> bccEmail) {
		super();
		this.fromUser = fromUser;
		this.subject = subject;
		this.bodyMessage = bodyMessage;
		this.toEmail = toEmail;
		this.ccEmail = ccEmail;
		this.bccEmail = bccEmail;
	}

	public EmailMessage(String fromUser, String subject, String bodyMessage, List<String> toEmail, List<String> ccEmail,
			List<String> bccEmail, List<String> serverFileIds) {
		super();
		this.fromUser = fromUser;
		this.subject = subject;
		this.bodyMessage = bodyMessage;
		this.toEmail = toEmail;
		this.ccEmail = ccEmail;
		this.bccEmail = bccEmail;
		this.serverFileIds = serverFileIds;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBodyMessage() {
		return bodyMessage;
	}

	public void setBodyMessage(String bodyMessage) {
		this.bodyMessage = bodyMessage;
	}

	public List<String> getBccEmail() {
		return bccEmail;
	}

	public void setBccEmail(List<String> bccEmail) {
		this.bccEmail = bccEmail;
	}

	public String getCustomMessageHeaderId() {
		return customMessageHeaderId;
	}

	public void setCustomMessageHeaderId(String customMessageHeaderId) {
		this.customMessageHeaderId = customMessageHeaderId;
	}

	public List<String> getToEmail() {
		return toEmail;
	}

	public void setToEmail(List<String> toEmail) {
		this.toEmail = toEmail;
	}

	public List<String> getCcEmail() {
		return ccEmail;
	}

	public void setCcEmail(List<String> ccEmail) {
		this.ccEmail = ccEmail;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public boolean getIsAuthReq() {
		return isAuthReq;
	}

	public void setIsAuthReq(boolean isAuthReq) {
		this.isAuthReq = isAuthReq;
	}

	public List<String> getServerFileIds() {
		return serverFileIds;
	}

	public void setServerFileIds(List<String> serverFileIds) {
		this.serverFileIds = serverFileIds;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	@Override
	public String toString() {
		return "EmailMessage [fromUser=" + fromUser + ", subject=" + subject + ", bodyMessage=" + bodyMessage
				+ ", toEmail=" + toEmail + ", ccEmail=" + ccEmail + ", bccEmail=" + bccEmail
				+ ", customMessageHeaderId=" + customMessageHeaderId + ", serverFileIds=" + serverFileIds
				+ ", messageId=" + messageId + ", isAuthReq=" + isAuthReq + ", attachments=" + attachments + "]";
	}

	
}
