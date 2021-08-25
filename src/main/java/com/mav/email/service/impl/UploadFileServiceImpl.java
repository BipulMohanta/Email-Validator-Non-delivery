package com.mav.email.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mav.email.constants.EnvironmentVariableConstants;
import com.mav.email.dto.UploadFileResponse;
import com.mav.email.entity.UploadedDocument;
import com.mav.email.exception.CustomRuntimeException;
import com.mav.email.exception.CustomServiceException;
import com.mav.email.manager.UploadFileManager;
import com.mav.email.service.UploadFileService;
import com.mav.email.util.GenericUtil;

@Service
public class UploadFileServiceImpl implements UploadFileService {

	@Autowired
	private Environment environment;

	@Autowired
	private UploadFileManager uploadFileManager;

	@Override
	public UploadFileResponse uploadFileInFileSystem(MultipartFile multipartFile) throws CustomServiceException {
		try {
			String serverFileName = String.valueOf(System.currentTimeMillis());
			File newFile = new File(environment.getProperty(EnvironmentVariableConstants.ATTACHEMENT_STORE_FILE_PATH)
					+ File.separator + serverFileName);
			try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
				fileOutputStream.write(multipartFile.getBytes());
			} catch (Exception e) {
				throw new CustomServiceException("", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			persistDocumentInfo(multipartFile, serverFileName);
			return new UploadFileResponse(multipartFile.getOriginalFilename(), serverFileName);
		} catch (Exception e) {
			throw new CustomServiceException("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void persistDocumentInfo(MultipartFile multipartFile, String serverFileName) throws CustomServiceException {
		try {
			UploadedDocument document = new UploadedDocument();
			String documentName = multipartFile.getOriginalFilename();
			if (StringUtils.isNotBlank(documentName)) {
				String[] arr = documentName.split("\\.");
				document.setDocumentExtension(arr[arr.length - 1]);
			}
			document.setDocumentName(documentName);
			document.setDocumentId(serverFileName);
			document.setDocumentSize(multipartFile.getResource().contentLength());
			document.setDocumentUploadedOnDate(GenericUtil.getCurrentDate());
			uploadFileManager.persistUploadedDocumentInfo(document);
		} catch (Exception e) {
			throw new CustomServiceException("", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public List<UploadFileResponse> uploadMultipleFileInFileSystem(MultipartFile[] files)
			throws CustomServiceException {

		List<UploadFileResponse> uploadFileResponses = new ArrayList<>();

		for (int i = 0; i < files.length; i++) {
			try {
				uploadFileResponses.add(uploadFileInFileSystem(files[i]));
			} catch (Exception e) {
				if (files[i] != null) {
					System.err.println(files[i].getOriginalFilename());
				}
			}
		}
		return uploadFileResponses;

	}

	@Override
	public List<UploadedDocument> getListOfUploadedDocument(List<String> serverFileIds) throws CustomServiceException {
		List<UploadedDocument> uploadedDocuments = null;
		try {
			uploadedDocuments = uploadFileManager.getListOfUploadedDocument(serverFileIds);
			if (CollectionUtils.isEmpty(uploadedDocuments)) {
				throw new CustomServiceException(HttpStatus.BAD_REQUEST);
			}

			validateUploadedDocumentsList(uploadedDocuments, serverFileIds);
		}catch (CustomServiceException e) {

		} catch (Exception e) {

		}
		return uploadedDocuments;
	}

	public void validateUploadedDocumentsList(List<UploadedDocument> uploadedDocuments, List<String> serverFileIds) {
		uploadedDocuments.stream().forEach(upd -> {
			if (!serverFileIds.contains(upd.getDocumentId())) {
				throw new CustomRuntimeException("", HttpStatus.BAD_REQUEST.value());
			}
		});
	}

}
