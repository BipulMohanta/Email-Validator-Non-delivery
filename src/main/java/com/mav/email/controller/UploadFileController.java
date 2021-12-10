package com.mav.email.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mav.email.dto.ResponseDTO;
import com.mav.email.dto.UploadFileResponse;
import com.mav.email.dto.factory.ResponseDTOFactory;
import com.mav.email.exception.CustomServiceException;
import com.mav.email.service.UploadFileService;

/**
 * 
 * @author bipul.mohanta
 *
 */
@RestController
@RequestMapping(path = "mail")
public class UploadFileController {

	@Autowired
	private UploadFileService uploadFileService;

	@Autowired
	@Qualifier(value = "responseDTOFactory")
	private ResponseDTOFactory responseDTOFactory;

	/**
	 * @author bipul.mohanta
	 * @param file
	 * @return
	 */
	@PostMapping("upload-single-file")
	public ResponseEntity<ResponseDTO> uploadSingleFile(@RequestParam("file") MultipartFile file) {
		try {
			UploadFileResponse uploadFileResponse = uploadFileService.uploadFileInFileSystem(file);
			return responseDTOFactory.reportOkStatus(null, uploadFileResponse);
		} catch (CustomServiceException exception) {
			return responseDTOFactory.reportGenericServerError(null, null, exception.getHttStatus());
		}
	}

	/**
	 * @author bipul.mohanta
	 * @param files
	 * @return
	 */
	@PostMapping("upload-multi-file")
	public ResponseEntity<ResponseDTO> uploadMultiFile(@RequestParam("file") MultipartFile[] files) {
		try {
			List<UploadFileResponse> uploadFileResponse = uploadFileService.uploadMultipleFileInFileSystem(files);
			return responseDTOFactory.reportOkStatus(null, uploadFileResponse);
		} catch (CustomServiceException exception) {
			return responseDTOFactory.reportGenericServerError(null, null, exception.getHttStatus());
		}
	}
}
