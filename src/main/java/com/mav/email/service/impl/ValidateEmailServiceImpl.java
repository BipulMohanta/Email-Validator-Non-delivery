package com.mav.email.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mav.email.service.ValidateEmailService;
import com.mav.email.util.SMTPMXLookup;

@Service("validateEmailService")
public class ValidateEmailServiceImpl implements ValidateEmailService {

	@Override
	@SuppressWarnings("unchecked")

	public Map<String, String> validateEmails(Map<String, Object> requestMap) {

		
		Map<String, String> resultMap = new HashMap<String, String>();		
		List<String> emailList = (List<String>) requestMap.get("emails");
		resultMap = SMTPMXLookup.invalidEmailAddress(emailList);
		return resultMap;
	}

}
