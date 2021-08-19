package com.mav.email.manager;

import com.mav.email.entity.RestLogs;

public interface RequestResponseLogManager {

	void persistReqResInDB(RestLogs restLogs);

}
