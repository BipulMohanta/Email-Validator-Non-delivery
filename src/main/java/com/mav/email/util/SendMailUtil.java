package com.mav.email.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.mav.email.bo.EmailMessage;
import com.sun.mail.smtp.SMTPMessage;

public class SendMailUtil extends MailUtil {
	public static EmailMessage sendMail(EmailMessage emailMessage) throws MessagingException {

		String bounceAddr = emailMessage.getBounceBackReciveEmail();

		String from = emailMessage.getFromUser();
		Session session = null;
		Properties props = System.getProperties();

		props.put("mail.smtp.host", "outlook.office365.com");
		if (Boolean.TRUE.equals(emailMessage.getIsAuthReq())) {
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");

			session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("", "");
				}
			});
		} else {
			session = Session.getInstance(props, null);
		}

		try {
			SMTPMessage message = new SMTPMessage(session);
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
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return emailMessage;
	}
}
