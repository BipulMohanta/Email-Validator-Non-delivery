package com.mav.email.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.mav.email.bo.EmailMessage;
import com.mav.email.service.SendEmailService;
import com.mav.email.util.SendMailUtil;

@Service("sendEmailService")
public class SendEmailServiceImpl implements SendEmailService {

	@Override
	public EmailMessage sendMailWithoutAttachment(EmailMessage emailMessage) throws MessagingException {

		SendMailUtil.sendMail(emailMessage);
		return emailMessage;

	}

	
	@Override
	public Map<String, Object> sendMailWithAttachment(EmailMessage emailMessage) throws MessagingException {

		SendMailUtil.sendMail(emailMessage);
		return null;

	}
	
	@SuppressWarnings("unchecked")
	private EmailMessage generateEmailEmailMessageObject(Map<String, Object> mailMetadataMap) {

		List<String> toEmail = (List<String>) mailMetadataMap.get("toEmailAddress");
		List<String> ccEmail = (List<String>) mailMetadataMap.get("ccEmailAddress");
		List<String> bccEmail = (List<String>) mailMetadataMap.get("bccEmailAddress");
		String subject = (String) mailMetadataMap.get("subject");
		String bodyMessage = (String) mailMetadataMap.get("bodyMessage");
		String fromUser = (String) mailMetadataMap.get("fromUser");

		EmailMessage emailMessage = new EmailMessage(fromUser, subject, bodyMessage, toEmail, ccEmail, bccEmail);

		UUID uuid = UUID.randomUUID();

		emailMessage.setCustomMessageHeaderId("MAIL_ENGINE_" + uuid.toString());
		return emailMessage;
	}

}
