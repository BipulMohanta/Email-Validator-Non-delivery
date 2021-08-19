package com.mav.email.dto.factory;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mav.email.dto.ResponseDTO;

@Component("responseDTOFactory")
public class ResponseDTOFactory {
	
	public ResponseEntity<ResponseDTO> reportOkStatus(List<String> message,Object response){
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		responseDTO.setSuccess(true);
		responseDTO.setCode(HttpStatus.OK.value());
		responseDTO.setMessage(message);
		responseDTO.setResponse(response);
		
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);
		
	}
	public ResponseEntity<ResponseDTO> reportBadRequestError(List<String> message,Object response){
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		responseDTO.setSuccess(true);
		responseDTO.setCode(HttpStatus.BAD_REQUEST.value());
		responseDTO.setMessage(message);
		responseDTO.setResponse(response);
		
		return new ResponseEntity<>(responseDTO,HttpStatus.BAD_REQUEST);
		
	}
	public ResponseEntity<ResponseDTO> reportInternalServerError(List<String> message,Object response){
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		responseDTO.setSuccess(false);
		responseDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		responseDTO.setMessage(message);
		responseDTO.setResponse(response);
		
		return new ResponseEntity<>(responseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
