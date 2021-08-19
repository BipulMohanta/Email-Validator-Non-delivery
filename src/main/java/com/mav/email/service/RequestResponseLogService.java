package com.mav.email.service;

import javax.servlet.http.HttpServletRequest;

public interface RequestResponseLogService {
	public void persistReqResInDB(HttpServletRequest request);
}
