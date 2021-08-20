package com.mav.email.controller;

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
import com.mav.email.exception.CustomServiceException;
import com.mav.email.service.SendEmailService;

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
	 * @author bipul.mohanta
	 * @param emailMessage
	 * @return
	 */
	@PostMapping(path = "sendMailWithoutAttachment")
	public ResponseEntity<ResponseDTO> sendMailWithoutAttachment(@RequestBody EmailMessage emailMessage) {
		try {
		
			emailMessage = sendEmailService.sendMailWithoutAttachment(emailMessage);
			return responseDTOFactory.reportOkStatus(null, emailMessage);
		} catch (CustomServiceException exception) {

			return null;

		} 
	}

}
