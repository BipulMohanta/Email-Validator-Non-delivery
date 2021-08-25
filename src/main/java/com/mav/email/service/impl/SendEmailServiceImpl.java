package com.mav.email.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mav.email.bo.Attachment;
import com.mav.email.bo.EmailMessage;
import com.mav.email.entity.SentMailDetails;
import com.mav.email.entity.UploadedDocument;
import com.mav.email.exception.CustomServiceException;
import com.mav.email.exception.ValidationException;
import com.mav.email.helper.SendMailHelper;
import com.mav.email.manager.SendMailManager;
import com.mav.email.service.SendEmailService;
import com.mav.email.service.UploadFileService;
import com.mav.email.util.GenericUtil;

@Service("sendEmailService")
public class SendEmailServiceImpl implements SendEmailService {

	@Autowired
	private SendMailHelper sendMailHelper;

	@Autowired
	private SendMailManager sendMailManager;

	@Autowired
	private UploadFileService uploadFileService;

	@Override
	public EmailMessage sendMailWithoutAttachment(EmailMessage emailMessage) throws CustomServiceException {
		try {
			GenericUtil.validateRequestEmailObject(emailMessage);
			persistEmailInDB(emailMessage);
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
	public EmailMessage sendMailWithAttachment(EmailMessage emailMessage) throws CustomServiceException {
		try {
			GenericUtil.validateRequestEmailObject(emailMessage);
			GenericUtil.validateRequestAttachmentFileIds(emailMessage);
			List<UploadedDocument> uploadedDocuments = uploadFileService
					.getListOfUploadedDocument(emailMessage.getServerFileIds());
			List<Attachment> attachementDocuments = sendMailHelper.castUploadedDocumentToAttachement(uploadedDocuments);
			emailMessage.setAttachments(attachementDocuments);
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
	public void persistEmailInDB(EmailMessage emailMessage) throws CustomServiceException {
		try {
			SentMailDetails sentMailDetails = sendMailHelper.createSentMailDetailsObject(emailMessage);
			sendMailManager.persistSendMailData(sentMailDetails);
		} catch (Exception exception) {
			throw new CustomServiceException("", exception, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}
