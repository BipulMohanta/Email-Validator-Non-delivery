package com.mav.email.dao;

import com.mav.email.entity.RestLogs;
/**
 * 
 * @author bipul.mohanta
 *
 */
public interface RequestResponseLogDao {
	/**
	 * 
	 * @param restLogs
	 */
	public void persistReqResInDB(RestLogs restLogs);
}
