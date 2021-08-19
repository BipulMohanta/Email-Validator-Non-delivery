package com.mav.email.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mav.email.bo.EmailMessage;
import com.mav.email.dto.ResponseDTO;
import com.mav.email.dto.factory.ResponseDTOFactory;
import com.mav.email.exception.CustomRuntimeException;
import com.mav.email.exception.ValidationException;
import com.mav.email.service.SendEmailService;
import com.mav.email.util.GenericUtil;

/**
 * 
 * @author APOORVA-BIPUL
 *
 */
@RestController
@RequestMapping(path = "api/v1/mail")
public class SendEmailController implements Mail {

	@Autowired
	@Qualifier(value = "sendEmailService")
	private SendEmailService sendEmailService;

	@Autowired
	@Qualifier(value = "responseDTOFactory")
	private ResponseDTOFactory responseDTOFactory;

	/**
	 * 
	 * @param emailMessage
	 * @return
	 */
	@PostMapping(path = "sendMailWithoutAttachment")
	public ResponseEntity<ResponseDTO> sendMailWithoutAttachment(@RequestBody EmailMessage emailMessage) {
		try {
			GenericUtil.ValidateRequestEmailObject(emailMessage);
			emailMessage = sendEmailService.sendMailWithoutAttachment(emailMessage);
			return responseDTOFactory.reportOkStatus(null, emailMessage);
		} catch (ValidationException e) {
			return null;
		} catch (CustomRuntimeException e) {

			return null;

		} catch (Exception e) {
			e.printStackTrace();
			List<String> messageString = new ArrayList<>();
			messageString.add(e.getMessage());
			return responseDTOFactory.reportInternalServerError(messageString, null);
		}
	}

}
