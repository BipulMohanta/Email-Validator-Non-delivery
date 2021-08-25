package com.mav.email.manager;

import java.util.List;

import com.mav.email.entity.UploadedDocument;

public interface UploadFileManager {
	public void persistUploadedDocumentInfo(UploadedDocument uploadedDocument);

	public List<UploadedDocument> getListOfUploadedDocument(List<String> serverFileIds);
}
