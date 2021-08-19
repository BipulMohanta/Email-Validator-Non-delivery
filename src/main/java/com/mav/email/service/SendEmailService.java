package com.mav.email.service;

import java.util.Map;

import javax.mail.MessagingException;

import com.mav.email.bo.EmailMessage;

/**
 * 
 * @author bipul.mohanta
 *
 */
public interface SendEmailService {
	/**
	 * @author bipul.mohanta
	 * @param emailMessage
	 * @return
	 * @throws MessagingException
	 */
	public EmailMessage sendMailWithoutAttachment(EmailMessage emailMessage) throws MessagingException;

	/**
	 * @author bipul.mohanta
	 * @param emailMessage
	 * @return
	 * @throws MessagingException
	 */
	public Map<String, Object> sendMailWithAttachment(EmailMessage emailMessage) throws MessagingException;

}
