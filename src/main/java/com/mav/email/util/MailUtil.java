package com.mav.email.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mav.email.bo.EmailMessage;
import com.mav.email.constants.MailConstants;
import com.sun.mail.imap.IMAPInputStream;

/**
 * 
 * @author APOORVA-BIPUL
 *
 */
public class MailUtil {
	
	public static Boolean syntaxValidateMail(String emailAddress) {
		return Pattern.matches(MailConstants.EMAIL_ADDRESS_REGEX, emailAddress);
	}
	
	public static String sendMailWithoutAttachement(EmailMessage emailMessage) {
		return null;
	}
	
	/**	
	   * CLM-15529	
	   * @author bipul.mohanta	
	   * @param properties	
	   * @param perTenantPropertyFile	
	   * @param company	
	   * @param store	
	   * @return	
	   */
		public static Map<String,Object> getAllBouncedMail(Properties properties, Map<String, String> serverSpecificUndeliveredPropertyFile,Store store) {
			Map<String,Object> resultMap = new HashMap<String, Object>();
			Message[] bounceBackMail = null;
			try {
				Session session = null;
				String[] folderPathArray = null;
				session = Session.getInstance(properties);

				store = session.getStore(serverSpecificUndeliveredPropertyFile.get(MailConstants.EMAIL_PROTOCOL_KEY));
				store.connect(serverSpecificUndeliveredPropertyFile.get(MailConstants.EMAIL_HOST_KEY),
						serverSpecificUndeliveredPropertyFile.get(MailConstants.BOUNCE_BACK_EMAIL_ACCOUNT), new String(""));

				// Get the path of the bounce back mail folder in the mail box
				String commaSeperatedPath = serverSpecificUndeliveredPropertyFile
						.get(MailConstants.BOUNCE_BACK_MAIL_FOLDER_PATH);

				Folder folder = store.getFolder(MailConstants.EMAIL_FOLDER_NAME);
				if (StringUtils.isNotBlank(commaSeperatedPath)) {
					folderPathArray = commaSeperatedPath.split("/");
				}
				if (folderPathArray != null && folderPathArray.length > 0) {
					for (int i = 0; i < folderPathArray.length; i++) {
						folder = folder.getFolder(folderPathArray[i]);
					}
				}

				if (!folder.exists()) {
					throw new FolderNotFoundException(folder, "Folder does not exists in the mail box");
				}
				folder.open(Folder.READ_WRITE);

				bounceBackMail = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
				for(int i=0;i<bounceBackMail.length;i++) {
					bounceBackMail[i].setFlag(Flags.Flag.SEEN, true);
				}
			} catch (AuthenticationFailedException e) {

			} catch (FolderNotFoundException e) {

			} catch (Exception e) {
			}
			resultMap.put("bounceBackMail", bounceBackMail);
			resultMap.put("store", store);
			return resultMap;
		}
		
		/**
		 * @author bipul.mohanta
		 * @param deliveryStatusString
		 * @return
		 */
		public static Map<String, Map<String, String>> parseDeliveryStatusMessage(String deliveryStatusString) {
			Map<String, Map<String, String>> consolidatedDeliveryStatusMap = new HashMap<>();
			Map<String, String> recipientDeliveryStatusMap = new HashMap<>();
			String[] deliveryStatusArray = deliveryStatusString.split(MailConstants.MAIL_LINE_DELIMITER);
			int counter = 1;
			for (String deliveryStatus : deliveryStatusArray) {
				int indexOfDoubleDot = deliveryStatus.indexOf(":");
				if (indexOfDoubleDot > -1) {

					String key = deliveryStatus.substring(0, indexOfDoubleDot).toLowerCase().trim();
					String value = deliveryStatus.substring(indexOfDoubleDot + 1, deliveryStatus.length()).trim();
					if (!recipientDeliveryStatusMap.containsKey(key)) {
						recipientDeliveryStatusMap.put(key, value);
					} else {
						consolidatedDeliveryStatusMap.put(MailConstants.RECIPIENT_PREFIX + counter,
								recipientDeliveryStatusMap);
						recipientDeliveryStatusMap = new HashMap<>();
						recipientDeliveryStatusMap.put(key, value);
						counter++;
					}
				}
			}

			consolidatedDeliveryStatusMap.put(MailConstants.RECIPIENT_PREFIX + counter,
					recipientDeliveryStatusMap);
			return consolidatedDeliveryStatusMap;
		}

		/**
		 * 
		 * @param message
		 * @param properties
		 * @param session
		 * @return Map<String,Object> -> Parse/read the entire bounce back mail and its
		 *         related attachments and notification report.
		 * @throws MessagingException
		 * @throws IOException
		 * @throws NoRecipientFoundInBounceMail
		 */
		public static Map<String, Object> fetchAllDatafromMail(Message message, Properties properties, Session session)
				throws MessagingException, IOException {
			
			
			Map<String, Object> returnMap = new HashMap<String, Object>();
			Map<String, Map<String, String>> consolidatedDeliveryStatusFailedRecipientMap = null;
			String bounceBackMailBody = "";
			String customHeaderId = "";
			if (message.getContentType().contains("multipart")) {
				Multipart multipart = (Multipart) message.getContent();

				for (int i = 0; i < multipart.getCount(); i++) {

					BodyPart bodyPart = multipart.getBodyPart(i);
					String disposition = bodyPart.getDisposition();
					String contentType = bodyPart.getContentType();

					if (MailConstants.MESSAGE_RFC_822.equalsIgnoreCase(contentType)
							|| (StringUtils.isNotBlank(disposition) && Part.ATTACHMENT.equalsIgnoreCase(disposition))) {
						InputStream originalSentMailInputStream = null;

						originalSentMailInputStream = bodyPart.getInputStream();
						session = Session.getInstance(properties);
						MimeMessage originalSentMail = new MimeMessage(session, originalSentMailInputStream);
						if (originalSentMail != null
								&& originalSentMail.getHeader(MailConstants.UNIQUE_CUSTOM_HEADER_ID) != null) {
							customHeaderId = originalSentMail
									.getHeader(MailConstants.UNIQUE_CUSTOM_HEADER_ID)[0];
						}

					} else if (MailConstants.MESSAGE_DELIVERY_STATUS
							.equalsIgnoreCase(multipart.getBodyPart(i).getContentType())) {

						consolidatedDeliveryStatusFailedRecipientMap = getAllUndeliveredEmailAddress(
								consolidatedDeliveryStatusFailedRecipientMap, properties, multipart.getBodyPart(i),
								message);

						if (consolidatedDeliveryStatusFailedRecipientMap == null
								|| consolidatedDeliveryStatusFailedRecipientMap.isEmpty()) {


						}

					} else if (multipart.getBodyPart(i).getContentType().contains("text/html")) {
						bounceBackMailBody = multipart.getBodyPart(i).getContent().toString();
					} else if (multipart.getBodyPart(i).getContentType()
							.contains(MailConstants.APPLICATION_OCTET_STREAM)) {
						InputStream messageBodyInputStream = null;
						try {
							messageBodyInputStream = (InputStream) multipart.getBodyPart(i).getContent();
							MimeMessage message1 = new MimeMessage(session, messageBodyInputStream);
							bounceBackMailBody = message1.getContent().toString();
						} catch (Exception e) {

						} finally {
							if (messageBodyInputStream != null) {
								messageBodyInputStream.close();
							}
						}
					} else if (multipart.getBodyPart(i).getContentType().toLowerCase()
							.contains("text/plain".toLowerCase())) {
						try {
							if (multipart.getBodyPart(i).getContent() != null) {
								bounceBackMailBody = multipart.getBodyPart(i).getContent().toString();
							}

						} catch (Exception e) {
							
						}
					}

				}

			}
			returnMap.put(MailConstants.CUSTOM_HEADER_ID, customHeaderId);
			returnMap.put(MailConstants.CONSOLIDATED_DELIVERY_STATUS_FAILED_RECIPIENT_MAP,
					consolidatedDeliveryStatusFailedRecipientMap);
			returnMap.put(MailConstants.BOUNCE_BACK_MAIL_BODY, bounceBackMailBody);
			return returnMap;
		}

		/**
		 * 
		 * @param consolidatedDeliveryStatusFailedRecipientMap
		 * @param properties
		 * @param bodyPart
		 * @param message
		 * @return Map<String, Map<String, String>> -> Fetches all the undelivered
		 *         recipient from the bounce back mail. There major ways to extract
		 *         these undelivered mail:- 1. Primary way: Using Delivery Notification
		 *         Status report 2. Secondary Way: By parsing the bounce mail content
		 *         and search the mail body for email addresses using regex.
		 */
		public static Map<String, Map<String, String>> getAllUndeliveredEmailAddress(
				Map<String, Map<String, String>> consolidatedDeliveryStatusFailedRecipientMap, Properties properties,
				BodyPart bodyPart, Message message) {

			boolean deliveryStatusMailExtractionFailed = false;
			try {

				Session session = Session.getInstance(properties);
				IMAPInputStream input = (IMAPInputStream) bodyPart.getContent();
				MimeMessage deliveryStatusMessage = new MimeMessage(session, input);
				if (deliveryStatusMessage != null && deliveryStatusMessage.getContent() != null
						&& StringUtils.isNotBlank(deliveryStatusMessage.getContent().toString())) {

					Map<String, Map<String, String>> consolidatedDeliveryStatusMap = MailUtil
							.parseDeliveryStatusMessage(deliveryStatusMessage.getContent().toString());
					Map<String, String> recipientDeliveryStatusMap = null;
					if (consolidatedDeliveryStatusMap != null) {

						consolidatedDeliveryStatusFailedRecipientMap = new HashMap<String, Map<String, String>>();
						int counter = 1;
						recipientDeliveryStatusMap = consolidatedDeliveryStatusMap
								.get(MailConstants.RECIPIENT_PREFIX + counter);
						while (recipientDeliveryStatusMap != null) {

							if ("failed".equalsIgnoreCase(recipientDeliveryStatusMap.get("action"))) {

								Pattern regexPattern = Pattern.compile(MailConstants.EMAIL_VALIDATOR_REGEX);
								Matcher matcher = regexPattern.matcher(recipientDeliveryStatusMap.get("final-recipient"));
								while (matcher.find()) {
									String email = matcher.group();

									addFailedRecipientToMap(consolidatedDeliveryStatusFailedRecipientMap, message,
											email.trim());

									break;
								}
							}
							counter++;
							recipientDeliveryStatusMap = consolidatedDeliveryStatusMap
									.get(MailConstants.RECIPIENT_PREFIX + counter);
						}
					}
				}
			} catch (Exception e) {
				deliveryStatusMailExtractionFailed = true;

			}

			try {
				if (deliveryStatusMailExtractionFailed || !(consolidatedDeliveryStatusFailedRecipientMap != null
						&& !consolidatedDeliveryStatusFailedRecipientMap.isEmpty())) {
					consolidatedDeliveryStatusFailedRecipientMap = new HashMap<>();
					Multipart multipart = (Multipart) message.getContent();
					for (int k = 0; k < multipart.getCount(); k++) {
						if (StringUtils.isNotEmpty(multipart.getBodyPart(k).getContentType())
								&& (multipart.getBodyPart(k).getContentType().contains("text/html"))) {
							Document jsoup = Jsoup.parse(multipart.getBodyPart(k).getContent().toString());
							Elements elements = jsoup.getElementsByAttribute("href");
							for (Element element : elements) {
								if (element.toString().toLowerCase().contains("mailto")) {

									addFailedRecipientToMap(consolidatedDeliveryStatusFailedRecipientMap, message,
											element.text().trim());

								}
							}
							break;
						} else if (multipart.getBodyPart(k).getContentType().contains("application/octet-stream")) {
							InputStream messageBodyInputStream = null;
							try {
								messageBodyInputStream = (InputStream) multipart.getBodyPart(k).getContent();
								Session session = Session.getInstance(properties);
								MimeMessage messageBody = new MimeMessage(session, messageBodyInputStream);

								if (messageBody.getContent() != null) {
									String messageBodyString = messageBody.getContent().toString();
									Pattern regexPattern = Pattern
											.compile("<" + MailConstants.EMAIL_VALIDATOR_REGEX + ">");
									Matcher matcher = regexPattern.matcher(messageBodyString);
									while (matcher.find()) {
										String email = matcher.group().replace("<", "").replace(">", "");

										addFailedRecipientToMap(consolidatedDeliveryStatusFailedRecipientMap, message,
												email.trim());

									}
								}

							} catch (Exception e) {

							} finally {
								if (messageBodyInputStream != null) {
									messageBodyInputStream.close();
								}
							}
							break;
						} else if (multipart.getBodyPart(k).getContentType().toLowerCase()
								.contains("text/plain".toLowerCase())) {
							try {
								if (multipart.getBodyPart(k).getContent() != null) {
									String messageBodyString = multipart.getBodyPart(k).getContent().toString();
									Pattern regexPattern = Pattern
											.compile("<" + MailConstants.EMAIL_VALIDATOR_REGEX + ">");
									Matcher matcher = regexPattern.matcher(messageBodyString);
									while (matcher.find()) {
										String email = matcher.group().replace("<", "").replace(">", "");

										addFailedRecipientToMap(consolidatedDeliveryStatusFailedRecipientMap, message,
												email.trim());
									}
								}

							} catch (Exception e) {
								
							}
							break;
						}
					}
				}
			} catch (Exception e) {

			}
			return consolidatedDeliveryStatusFailedRecipientMap;

		}

		private static void addFailedRecipientToMap(
				Map<String, Map<String, String>> consolidatedDeliveryStatusFailedRecipientMap, Message message,
				String email) {

			Map<String, String> deliveryStatusFailedRecipientMap = new HashMap<String, String>();
			deliveryStatusFailedRecipientMap.put(MailConstants.EMAIL_ADDRESS, email);
			try {
				deliveryStatusFailedRecipientMap.put(MailConstants.EMAIL_DELIVERY_TIME,
						String.valueOf(message.getReceivedDate().getTime()));
			} catch (MessagingException e) {
				
			}
			consolidatedDeliveryStatusFailedRecipientMap.put(email, deliveryStatusFailedRecipientMap);
		}
}
