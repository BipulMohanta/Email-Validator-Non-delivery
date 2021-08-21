package com.mav.email.helper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.mav.email.bo.EmailMessage;
import com.mav.email.constants.EnvironmentVariableConstants;
import com.mav.email.constants.MailConstants;
import com.mav.email.exception.CustomServiceException;
import com.sun.mail.smtp.SMTPMessage;

@Component
public class SendMailHelper {

	@Autowired
	private Environment environment;

	/**
	 * 
	 * @param emailMessage
	 * @return
	 * @throws CustomServiceException
	 */
	public EmailMessage sendMail(EmailMessage emailMessage) throws CustomServiceException {

		Properties mailProperties = getMailProperty(emailMessage.getIsAuthReq());
		Session sessionProperties = getMailSession(mailProperties, emailMessage.getIsAuthReq(),
				environment.getProperty(EnvironmentVariableConstants.EMAIL_SERVICE_MAIN_ACCOUNT_EMAIL_ADDRESS),
				environment
						.getProperty(EnvironmentVariableConstants.EMAIL_SERVICE_MAIN_ACCOUNT_EMAIL_ADDRESS_PASSWORD));
		String from = environment.getProperty(EnvironmentVariableConstants.EMAIL_SERVICE_MAIN_ACCOUNT_EMAIL_ADDRESS);

		try {
			SMTPMessage message = new SMTPMessage(sessionProperties);

			message.addHeader("customHeader", emailMessage.getCustomMessageHeaderId());

			message.setFrom(new InternetAddress(from));

			message.setSubject(emailMessage.getSubject(), "text/html");
			message.setContent(emailMessage.getBodyMessage(), "text/html");

			addEmailAddressToMessage(message, Message.RecipientType.TO, emailMessage.getToEmail());
			addEmailAddressToMessage(message, Message.RecipientType.CC, emailMessage.getCcEmail());
			addEmailAddressToMessage(message, Message.RecipientType.BCC, emailMessage.getBccEmail());

			message.setNotifyOptions(SMTPMessage.NOTIFY_FAILURE);
			message.setEnvelopeFrom(
					environment.getProperty(EnvironmentVariableConstants.EMAIL_SERVICE_BOUNCE_BACK_EMAIL_ADDRESS));

			if (CollectionUtils.isNotEmpty(emailMessage.getAttachments())) {
				Multipart multipart = new MimeMultipart();
				for (int i = 0; i < emailMessage.getAttachments().size(); i++) {
					MimeBodyPart attachmentPart = new MimeBodyPart();
					attachmentPart.attachFile(new File(emailMessage.getAttachments().get(i).getActualFilePath()));
					multipart.addBodyPart(attachmentPart);
				}
				message.setContent(multipart);
			}

			Transport.send(message);

			emailMessage.setMessageId(message.getMessageID());
		} catch (MessagingException exception) {
			throw new CustomServiceException("", exception, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception exception) {
			throw new CustomServiceException("", exception, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return emailMessage;
	}

	/**
	 * 
	 * @param message
	 * @param type
	 * @param emailList
	 * @throws MessagingException
	 */
	public void addEmailAddressToMessage(SMTPMessage message, RecipientType type, List<String> emailList)
			throws MessagingException {
		InternetAddress[] emailAddressesArray = new InternetAddress[emailList.size()];
		for (int i = 0; i < emailList.size(); i++) {
			emailAddressesArray[i] = new InternetAddress(emailList.get(i));
		}
		message.addRecipients(type, emailAddressesArray);

	}

	/**
	 * @author bipul.mohanta
	 * @param props
	 * @param isAuthReq
	 * @param userName
	 * @param password
	 * @return
	 */
	public Session getMailSession(Properties props, boolean isAuthReq, String userName, String password) {
		Session session = null;
		if (Boolean.TRUE.equals(isAuthReq)) {

			session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			});
		} else {
			session = Session.getInstance(props, null);
		}
		return session;
	}

	/**
	 * @author bipul.mohanta
	 * @param isAuthReq
	 * @return
	 */
	public Properties getMailProperty(boolean isAuthReq) {
		Properties props = System.getProperties();
		props.put(MailConstants.MAIL_SMTP_HOST_KEY,
				environment.getProperty(EnvironmentVariableConstants.EMAIL_SMTP_HOST_NAME));
		if (Boolean.TRUE.equals(isAuthReq)) {
			props.put(MailConstants.MAIL_SMTP_AUTHENTICATION_KEY, "true");
			props.put(MailConstants.MAIL_SMTP_STARTTLS_ENABLE_KEY, "true");
			props.put(MailConstants.MAIL_SMTP_PORT_KEY, EnvironmentVariableConstants.EMAIL_SMTP_PORT_NUMBER);

		}
		return props;
	}

}
