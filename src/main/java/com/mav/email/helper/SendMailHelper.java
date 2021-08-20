package com.mav.email.helper;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.mav.email.bo.EmailMessage;
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
	public  EmailMessage sendMail(EmailMessage emailMessage) throws CustomServiceException {

		String bounceAddr = emailMessage.getBounceBackReciveEmail();

		String from = emailMessage.getFromUser();

		Properties mailProperties = getMailProperty(emailMessage.getIsAuthReq());
		Session sessionProperties = getMailSession(mailProperties, emailMessage.getIsAuthReq(),"","");

		try {
			SMTPMessage message = new SMTPMessage(sessionProperties);
			
			message.addHeader("customHeader", emailMessage.getCustomMessageHeaderId());
			
			message.setFrom(new InternetAddress(from));

			message.setSubject(emailMessage.getSubject(), "text/html");
			message.setContent(emailMessage.getBodyMessage(), "text/html");

			InternetAddress[] toAddressess = new InternetAddress[emailMessage.getToEmail().size()];
			for (int i = 0; i < emailMessage.getToEmail().size(); i++) {
				toAddressess[i] = new InternetAddress(emailMessage.getToEmail().get(i));
			}
			message.addRecipients(Message.RecipientType.TO, toAddressess);

			InternetAddress[] ccAddresses = new InternetAddress[emailMessage.getCcEmail().size()];
			for (int i = 0; i < emailMessage.getCcEmail().size(); i++) {
				ccAddresses[i] = new InternetAddress(emailMessage.getCcEmail().get(i));
			}
			message.addRecipients(Message.RecipientType.CC, ccAddresses);

			InternetAddress[] bccAddresses = new InternetAddress[emailMessage.getBccEmail().size()];
			for (int i = 0; i < emailMessage.getBccEmail().size(); i++) {
				bccAddresses[i] = new InternetAddress(emailMessage.getBccEmail().get(i));
			}
			message.addRecipients(Message.RecipientType.BCC, bccAddresses);

			message.setNotifyOptions(SMTPMessage.NOTIFY_FAILURE);
			message.setEnvelopeFrom(bounceAddr);

			Multipart multipart = new MimeMultipart();
			for (int i = 0; i < emailMessage.getAttachments().size(); i++) {
				MimeBodyPart attachmentPart = new MimeBodyPart();
				attachmentPart.attachFile(new File(emailMessage.getAttachments().get(i).getActualFilePath()));
				multipart.addBodyPart(attachmentPart);
			}
			message.setContent(multipart);

			message.saveChanges();
			Transport.send(message);

			emailMessage.setMessageId(message.getMessageID());
		} catch (MessagingException | IOException exception) {
			throw new CustomServiceException("", exception, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception exception) {
			throw new CustomServiceException("", exception, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return emailMessage;
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
		props.put(MailConstants.MAIL_SMTP_HOST_KEY, "outlook.office365.com");
		if (Boolean.TRUE.equals(isAuthReq)) {
			props.put(MailConstants.MAIL_SMTP_AUTHENTICATION_KEY, "true");
			props.put(MailConstants.MAIL_SMTP_STARTTLS_ENABLE_KEY, "true");
			props.put(MailConstants.MAIL_SMTP_PORT_KEY, "587");

		}
		return props;
	}


}
