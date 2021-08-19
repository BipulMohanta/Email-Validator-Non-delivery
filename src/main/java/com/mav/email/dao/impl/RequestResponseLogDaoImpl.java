package com.mav.email.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mav.email.dao.RequestResponseLogDao;
import com.mav.email.entity.RestLogs;

@Repository
public class RequestResponseLogDaoImpl implements RequestResponseLogDao {

	@PersistenceContext(unitName = "emailDB")
	private EntityManager entityManager;

	@Override
	public void persistReqResInDB(RestLogs restLogs) {
		entityManager.persist(restLogs);
	}

}
