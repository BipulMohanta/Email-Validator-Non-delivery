package com.mav.email.constants;

public class MailConstants {
	private MailConstants() {
		throw new IllegalStateException("Mail Constants class");
	}

	public static final String EMAIL_PORT_KEY = "email_port";
	public static final String EMAIL_PROTOCOL_KEY = "email_protocol";
	public static final String EMAIL_HOST_KEY = "email_host";
	public static final String EMAIL_FOLDER_NAME = "INBOX";

	public static final String BOUNCE_BACK_EMAIL_ACCOUNT = "BOUNCE_BACK_EMAIL_ACCOUNT";
	public static final String BOUNCE_BACK_EMAIL_ADDRESS = "BOUNCE_BACK_EMAIL_ADDRESS";
	public static final String BOUNCE_BACK_EMAIL_ACCOUNT_PASSWORD = "BOUNCE_BACK_EMAIL_ACCOUNT_PASSWORD";
	public static final String BOUNCE_BACK_MAIL_FOLDER_PATH = "BOUNCE_BACK_MAIL_FOLDER_PATH";
	public static final String UNIQUE_CUSTOM_HEADER_ID = "mailHeaderId";
	public static final String RECIPIENT_PREFIX = "recipient_";
	public static final String MAIL_LINE_DELIMITER = "\r\n";
	public static final String EMAIL_VALIDATOR_REGEX = "[a-zA-Z0-9_\\-\\.]+@[a-zA-Z0-9_\\-\\.]+\\.[a-zA-Z]{2,5}";
	public static final String UNDELIVERED_MAIL_RECIPIENT = "undeliveredMailRecipient";
	public static final String EMAIL_ADDRESS = "emailAddress";
	public static final String EMAIL_DELIVERY_TIME = "emailDeliveryTime";
	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	public static final String TIME_FORMAT = "hh:mm aaa";
	public static final String SENDER_NAME = "SENDER_NAME";
	public static final String EMAIL_SUBJECT = "EMAIL_SUBJECT";
	public static final String RECIPIENT_EMAIL_ID = "RECIPIENT_EMAIL_ID";
	public static final String MESSAGE_RFC_822 = "message/rfc822";
	public static final String MESSAGE_DELIVERY_STATUS = "message/delivery-status";
	public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
	public static final String CUSTOM_MAIL_HEADER_ID_REGEX = "@@[a-zA-Z0-9\\-_]+@[a-zA-Z0-9\\-_]+@@";
	public static final String CUSTOM_HEADER_ID = "customHeaderId";
	public static final String CONSOLIDATED_DELIVERY_STATUS_FAILED_RECIPIENT_MAP = "consolidatedDeliveryStatusFailedRecipientMap";
	public static final String BOUNCE_BACK_MAIL_BODY = "bounceBackMailBody";
	public static final String MESSAGE_ID = "Message-Id";

	public static final String MAIL_SMTP_HOST_KEY = "mail.smtp.host";
	public static final String MAIL_SMTP_AUTHENTICATION_KEY = "mail.smtp.auth";
	public static final String MAIL_SMTP_STARTTLS_ENABLE_KEY = "mail.smtp.starttls.enable";
	public static final String MAIL_SMTP_PORT_KEY = "mail.smtp.port";

}
