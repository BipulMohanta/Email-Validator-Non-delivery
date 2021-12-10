package com.mav.email.dto.factory;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mav.email.dto.ResponseDTO;

/**
 * 
 * @author bipul.mohanta
 *
 */
@Component("responseDTOFactory")
public class ResponseDTOFactory {
	/**
	 * 
	 * @param message
	 * @param response
	 * @return
	 */
	public ResponseEntity<ResponseDTO> reportOkStatus(List<String> message, Object response) {

		ResponseDTO responseDTO = new ResponseDTO();

		responseDTO.setSuccess(true);
		responseDTO.setCode(HttpStatus.OK.value());
		responseDTO.setMessage(message);
		responseDTO.setResponse(response);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * 
	 * @param message
	 * @param response
	 * @return
	 */
	public ResponseEntity<ResponseDTO> reportBadRequestError(List<String> message, Object response) {

		ResponseDTO responseDTO = new ResponseDTO();

		responseDTO.setSuccess(false);
		responseDTO.setCode(HttpStatus.BAD_REQUEST.value());
		responseDTO.setMessage(message);
		responseDTO.setResponse(response);

		return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);

	}

	/**
	 * 
	 * @param message
	 * @param response
	 * @return
	 */
	public ResponseEntity<ResponseDTO> reportInternalServerError(List<String> message, Object response) {

		ResponseDTO responseDTO = new ResponseDTO();

		responseDTO.setSuccess(false);
		responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		responseDTO.setMessage(message);
		responseDTO.setResponse(response);

		return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	/**
	 * 
	 * @param message
	 * @param response
	 * @param httpStatus
	 * @return
	 */
	public ResponseEntity<ResponseDTO> reportGenericServerError(List<String> message, Object response,
			HttpStatus httpStatus) {

		ResponseDTO responseDTO = new ResponseDTO();

		responseDTO.setSuccess(false);
		responseDTO.setCode(httpStatus.value());
		responseDTO.setMessage(message);
		responseDTO.setResponse(response);

		return new ResponseEntity<>(responseDTO, httpStatus);

	}

}
