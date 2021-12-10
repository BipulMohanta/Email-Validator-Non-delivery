package com.mav.email.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mav.email.dto.ResponseDTO;
import com.mav.email.dto.factory.ResponseDTOFactory;
import com.mav.email.exception.CustomServiceException;
import com.mav.email.service.ValidateEmailService;
import com.mav.email.util.GenericUtil;
import com.mav.email.util.SMTPMXLookup;

/**
 * 
 * @author bipul.mohanta
 *
 */
@RestController
@RequestMapping(path = "mail")
public class ValidateEmailController {

	@Autowired
	@Qualifier(value = "validateEmailService")
	private ValidateEmailService validateEmailService;

	@Autowired
	@Qualifier(value = "responseDTOFactory")
	private ResponseDTOFactory responseDTOFactory;

	/**
	 * 
	 * @param requestPayload
	 * @return
	 */
	@PostMapping(path = "validate-emails")
	public ResponseEntity<ResponseDTO> validateEmailsList(@RequestBody String requestPayload) {
		JSONObject requestJSON = null;
		Map<String, Object> requestMap = null;
		try {
			requestJSON = GenericUtil.validateJSON(requestPayload);
			if (requestJSON != null && requestJSON.length() > 0) {
				requestMap = GenericUtil.convertJSONObjectToMap(requestJSON);
				Map<String, String> resultMap = validateEmailService.validateEmails(requestMap);
				return responseDTOFactory.reportOkStatus(null, resultMap);

			} else {

				List<String> messageString = new ArrayList<>();
				messageString.add("Empty Json");
				return responseDTOFactory.reportBadRequestError(messageString, null);
			}
		} catch (Exception exception) {
			return null;

		}

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(path = { "domain-mx-record" })
	public ResponseEntity<ResponseDTO> getMXRecordForDomain(HttpServletRequest request, HttpServletResponse response) {

		try {

			String domainName = request.getParameter("domainName");
			if (domainName == null || domainName.isEmpty()) {
				domainName = request.getHeader("domainName");
			}
			List<String> mxList = SMTPMXLookup.getMX(domainName);
			return responseDTOFactory.reportOkStatus(null, mxList);
		} catch (Exception exception) {
			return null;

		}

	}
}
