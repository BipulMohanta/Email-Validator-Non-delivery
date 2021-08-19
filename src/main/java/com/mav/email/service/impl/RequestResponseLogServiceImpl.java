package com.mav.email.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mav.email.entity.RestLogs;
import com.mav.email.manager.RequestResponseLogManager;
import com.mav.email.service.RequestResponseLogService;
@Service
public class RequestResponseLogServiceImpl implements RequestResponseLogService{

	@Autowired
	private RequestResponseLogManager requestResponseLogManager;
	@Override
	public void persistReqResInDB(HttpServletRequest request) {

		RestLogs logs = new RestLogs();
		logs.setRequestURL(request.getRequestURL().toString());
		logs.setRequestReceivedOn(new Date());
		requestResponseLogManager.persistReqResInDB(logs);
		
	}

}
