package com.mav.email.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author bipul.mohanta
 *
 */

@Entity
@Table(name = "SENT_MAIL_DETAILS")
public class SentMailDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SENT_MAIL_ID", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "FROM_EMAIL_ADDRESS")
	private String fromEmailAddress;

	@Column(name = "TO_EMAIL_ADDRESS")
	private String toEmailAddress;

	@Column(name = "CC_EMAIL_ADDRESS")
	private String ccEmailAddress;

	@Column(name = "BCC_EMAIL_ADDRESS")
	private String bccEmailAddress;

	@Column(name = "EMAIL_SENT_ON")
	private Date emailSentOn;

	@Column(name = "EMAIL_MESSAGE_ID")
	private String emailMessageId;

	@Column(name = "CUSTOM_MESSAGE_ID")
	private String customMessageId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sentMailDetails", cascade = CascadeType.ALL)
	private List<EmailAttachement> emailAttachements;

	public SentMailDetails() {
	}

	public SentMailDetails(String fromEmailAddress, String toEmailAddress, String ccEmailAddress,
			String bccEmailAddress, Date emailSentOn, String emailMessageId, String customMessageId,
			List<EmailAttachement> emailAttachements) {
		super();
		this.fromEmailAddress = fromEmailAddress;
		this.toEmailAddress = toEmailAddress;
		this.ccEmailAddress = ccEmailAddress;
		this.bccEmailAddress = bccEmailAddress;
		this.emailSentOn = emailSentOn;
		this.emailMessageId = emailMessageId;
		this.customMessageId = customMessageId;
		this.emailAttachements = emailAttachements;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFromEmailAddress() {
		return fromEmailAddress;
	}

	public void setFromEmailAddress(String fromEmailAddress) {
		this.fromEmailAddress = fromEmailAddress;
	}

	public String getToEmailAddress() {
		return toEmailAddress;
	}

	public void setToEmailAddress(String toEmailAddress) {
		this.toEmailAddress = toEmailAddress;
	}

	public String getCcEmailAddress() {
		return ccEmailAddress;
	}

	public void setCcEmailAddress(String ccEmailAddress) {
		this.ccEmailAddress = ccEmailAddress;
	}

	public String getBccEmailAddress() {
		return bccEmailAddress;
	}

	public void setBccEmailAddress(String bccEmailAddress) {
		this.bccEmailAddress = bccEmailAddress;
	}

	public Date getEmailSentOn() {
		return emailSentOn;
	}

	public void setEmailSentOn(Date emailSentOn) {
		this.emailSentOn = emailSentOn;
	}

	public String getEmailMessageId() {
		return emailMessageId;
	}

	public void setEmailMessageId(String emailMessageId) {
		this.emailMessageId = emailMessageId;
	}

	public String getCustomMessageId() {
		return customMessageId;
	}

	public void setCustomMessageId(String customMessageId) {
		this.customMessageId = customMessageId;
	}

	public List<EmailAttachement> getEmailAttachements() {
		return emailAttachements;
	}

	public void setEmailAttachements(List<EmailAttachement> emailAttachements) {
		this.emailAttachements = emailAttachements;
	}

}
