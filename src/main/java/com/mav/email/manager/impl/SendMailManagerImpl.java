package com.mav.email.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mav.email.bo.EmailMessage;
import com.mav.email.dao.SendMailDao;
import com.mav.email.entity.SentMailDetails;
import com.mav.email.manager.SendMailManager;

/**
 * 
 * @author bipul.mohanta
 *
 */
@Component
@Transactional(readOnly = false,transactionManager="emailTxManager")
public class SendMailManagerImpl implements SendMailManager {
	@Autowired
	private SendMailDao sendMailDao;

	@Override
	public void persistSendMailData(SentMailDetails sentMailDetails) {
		sendMailDao.persistSendMailData(sentMailDetails);
	}

}
