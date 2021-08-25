package com.mav.email.constants;

/**
 * 
 * @author bipul.mohanta
 *
 */
public class EnvironmentVariableConstants {

	private EnvironmentVariableConstants() {
		throw new IllegalStateException("Environment Variable Constants class");
	}

	public static final String EMAIL_SERVICE_BOUNCE_BACK_EMAIL_ADDRESS = "email.service.bounce-back-emailaddress";
	public static final String EMAIL_SERVICE_BOUNCE_BACK_EMAIL_ADDRESS_PASSWORD = "email.service.bounce-back-emailaddress-password";

	public static final String EMAIL_SERVICE_MAIN_ACCOUNT_EMAIL_ADDRESS = "email.service.main-account-email-address";
	public static final String EMAIL_SERVICE_MAIN_ACCOUNT_EMAIL_ADDRESS_PASSWORD = "email.service.main-account-email-address-password";
	public static final String EMAIL_SMTP_HOST_NAME = "email.smtp.host.name";
	public static final String EMAIL_SMTP_PORT_NUMBER = "email.smtp.port.number";
	public static final String ATTACHEMENT_STORE_FILE_PATH = "attachement.store.file.path";
}
