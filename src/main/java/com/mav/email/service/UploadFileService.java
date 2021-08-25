package com.mav.email.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mav.email.dto.UploadFileResponse;
import com.mav.email.entity.UploadedDocument;
import com.mav.email.exception.CustomServiceException;

public interface UploadFileService {

	UploadFileResponse uploadFileInFileSystem(MultipartFile file) throws CustomServiceException;

	void persistDocumentInfo(MultipartFile multipartFile, String serverFileName) throws CustomServiceException;

	List<UploadFileResponse> uploadMultipleFileInFileSystem(MultipartFile[] files) throws CustomServiceException;

	List<UploadedDocument> getListOfUploadedDocument(List<String> serverFileIds) throws CustomServiceException;

}
