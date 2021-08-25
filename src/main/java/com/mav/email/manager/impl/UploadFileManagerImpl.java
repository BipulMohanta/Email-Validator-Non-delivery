package com.mav.email.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mav.email.dao.UploadFileDao;
import com.mav.email.entity.UploadedDocument;
import com.mav.email.manager.UploadFileManager;

@Component
@Transactional(readOnly = false, transactionManager = "emailTxManager")
public class UploadFileManagerImpl implements UploadFileManager {

	@Autowired
	private UploadFileDao uploadFileDao;
	
	@Override
	public void persistUploadedDocumentInfo(UploadedDocument uploadedDocument) {
		
		uploadFileDao.persistUploadedDocumentInfo(uploadedDocument);
	}

	@Override
	public List<UploadedDocument> getListOfUploadedDocument(List<String> serverFileIds) {
		return uploadFileDao.getListOfUploadedDocument(serverFileIds);
	}

	
	
}
