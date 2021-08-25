package com.mav.email.dao;

import com.mav.email.bo.EmailMessage;
import com.mav.email.entity.SentMailDetails;

/**
 * 
 * @author bipul.mohanta
 *
 */
public interface SendMailDao {
	/**
	 * 
	 * @param emailMessage
	 */
	void persistSendMailData(SentMailDetails sentMailDetails);

}
