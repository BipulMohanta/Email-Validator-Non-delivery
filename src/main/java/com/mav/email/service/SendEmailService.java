package com.mav.email.service;

import java.util.Map;

import javax.mail.MessagingException;

import com.mav.email.bo.EmailMessage;
import com.mav.email.exception.CustomServiceException;

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
	public EmailMessage sendMailWithoutAttachment(EmailMessage emailMessage) throws CustomServiceException;

	/**
	 * @author bipul.mohanta
	 * @param emailMessage
	 * @return
	 * @throws MessagingException
	 */
	public EmailMessage sendMailWithAttachment(EmailMessage emailMessage) throws CustomServiceException;

}
