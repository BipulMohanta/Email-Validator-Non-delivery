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
 * @author bipul.mohanta
 *
 */
@RestController
@RequestMapping(path = "mail")
public class SendEmailController {

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

			return responseDTOFactory.reportGenericServerError(null, null, exception.getHttStatus());

		}
	}

	/**
	 * @author bipul.mohanta
	 * @param emailMessage
	 * @return
	 */
	@PostMapping(path = "sendMailWithAttachment")
	public ResponseEntity<ResponseDTO> sendMailWithAttachment(@RequestBody EmailMessage emailMessage) {
		try {
			emailMessage = sendEmailService.sendMailWithAttachment(emailMessage);
			return responseDTOFactory.reportOkStatus(null, emailMessage);
		} catch (CustomServiceException exception) {

			return responseDTOFactory.reportGenericServerError(null, null, exception.getHttStatus());

		}
	}

}
