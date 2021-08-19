package com.mav.email.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mav.email.dao.RequestResponseLogDao;
import com.mav.email.entity.RestLogs;
import com.mav.email.manager.RequestResponseLogManager;

@Component
@Transactional(readOnly = false,transactionManager="emailTxManager")
public class RequestResponseLogManagerImpl implements RequestResponseLogManager{
	
	@Autowired
	private RequestResponseLogDao requestResponseLogDao;
	
	@Override
	public void persistReqResInDB(RestLogs restLogs) {
		requestResponseLogDao.persistReqResInDB(restLogs);
	}
}
