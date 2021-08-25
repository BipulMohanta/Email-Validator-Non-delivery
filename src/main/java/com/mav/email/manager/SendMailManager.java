package com.mav.email.manager;

import com.mav.email.bo.EmailMessage;
import com.mav.email.entity.SentMailDetails;

public interface SendMailManager {
	public void persistSendMailData(SentMailDetails sentMailDetails);
}
