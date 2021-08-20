package com.mav.email.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mav.email.bo.EmailMessage;
import com.mav.email.exception.CustomServiceException;
import com.mav.email.exception.ValidationException;
import com.mav.email.helper.SendMailHelper;
import com.mav.email.service.SendEmailService;
import com.mav.email.util.GenericUtil;

@Service("sendEmailService")
public class SendEmailServiceImpl implements SendEmailService {

	@Autowired
	private SendMailHelper sendMailHelper;
	
	@Override
	public EmailMessage sendMailWithoutAttachment(EmailMessage emailMessage) throws CustomServiceException {
		try {
			GenericUtil.ValidateRequestEmailObject(emailMessage);
			sendMailHelper.sendMail(emailMessage);
			return emailMessage;
		} catch (ValidationException exception) {
			throw new CustomServiceException(exception.getErrorMessage(), exception, exception.gethttpStatus());
		} catch (CustomServiceException exception) {
			return null;

		} catch (Exception exception) {
			throw new CustomServiceException("", exception, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@Override
	public Map<String, Object> sendMailWithAttachment(EmailMessage emailMessage) throws CustomServiceException {
		try {
			GenericUtil.ValidateRequestEmailObject(emailMessage);

			sendMailHelper.sendMail(emailMessage);
			return null;
		} catch (ValidationException exception) {
			throw new CustomServiceException(exception.getErrorMessage(), exception, exception.gethttpStatus());
		} catch (CustomServiceException exception) {
			return null;
		} catch (Exception exception) {
			throw new CustomServiceException("", exception, HttpStatus.INTERNAL_SERVER_ERROR);

		}
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
