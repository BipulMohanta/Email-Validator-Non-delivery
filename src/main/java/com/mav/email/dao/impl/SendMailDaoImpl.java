package com.mav.email.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mav.email.bo.EmailMessage;
import com.mav.email.dao.SendMailDao;
import com.mav.email.entity.SentMailDetails;

@Repository
public class SendMailDaoImpl implements SendMailDao {
	@PersistenceContext(unitName = "emailDB")
	private EntityManager entityManager;

	@Override
	public void persistSendMailData(SentMailDetails sentMailDetails) {

		entityManager.persist(sentMailDetails);
		
	}

}
