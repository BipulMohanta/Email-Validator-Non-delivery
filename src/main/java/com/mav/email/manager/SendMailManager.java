package com.mav.email.manager;

import com.mav.email.bo.EmailMessage;

public interface SendMailManager {
	public void persistSendMailData(EmailMessage emailMessage);
}
