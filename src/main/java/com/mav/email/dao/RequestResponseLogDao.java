package com.mav.email.dao;

import com.mav.email.entity.RestLogs;

public interface RequestResponseLogDao {
	public void persistReqResInDB(RestLogs restLogs);
}
