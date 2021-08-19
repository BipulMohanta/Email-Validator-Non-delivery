package com.mav.email.bo;

import java.util.List;
/**
 * 
 * @author bipul.mohanta
 *
 */
public class EmailMessage {

	private String fromUser;
	private String bounceBackReciveEmail;
	private String subject;
	private String bodyMessage;
	private List<String> toEmail;
	private List<String> ccEmail;
	private List<String> bccEmail;
	private String customMessageHeaderId;
	private List<Attachment> attachments;
	private String messageId;
	private boolean isAuthReq;

	public EmailMessage() {
		throw new IllegalStateException("");
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

	public EmailMessage(String fromUser, String bounceBackReciveEmail, String subject, String bodyMessage,
			List<String> toEmail, List<String> ccEmail, List<String> bccEmail, List<Attachment> attachments) {
		super();
		this.fromUser = fromUser;
		this.bounceBackReciveEmail = bounceBackReciveEmail;
		this.subject = subject;
		this.bodyMessage = bodyMessage;
		this.toEmail = toEmail;
		this.ccEmail = ccEmail;
		this.bccEmail = bccEmail;
		this.attachments = attachments;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getBounceBackReciveEmail() {
		return bounceBackReciveEmail;
	}

	public void setBounceBackReciveEmail(String bounceBackReciveEmail) {
		this.bounceBackReciveEmail = bounceBackReciveEmail;
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

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
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

	@Override
	public String toString() {
		return "";

	}
}
