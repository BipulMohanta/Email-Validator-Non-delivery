package com.mav.email.service;

import java.util.Map;

public interface ValidateEmailService {

	Map<String, String> validateEmails(Map<String, Object> requestMap);

}
