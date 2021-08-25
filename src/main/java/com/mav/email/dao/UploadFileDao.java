package com.mav.email.dao;

import java.util.List;

import com.mav.email.entity.UploadedDocument;

public interface UploadFileDao {

	void persistUploadedDocumentInfo(UploadedDocument uploadedDocument);

	List<UploadedDocument> getListOfUploadedDocument(List<String> serverFileIds);

}
