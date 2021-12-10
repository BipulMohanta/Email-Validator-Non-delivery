package com.mav.email.dao;

import java.util.List;

import com.mav.email.entity.UploadedDocument;

/**
 * 
 * @author bipul.mohanta
 *
 */
public interface UploadFileDao {
	/**
	 * 
	 * @param uploadedDocument
	 */
	void persistUploadedDocumentInfo(UploadedDocument uploadedDocument);

	/**
	 * 
	 * @param serverFileIds
	 * @return
	 */
	List<UploadedDocument> getListOfUploadedDocument(List<String> serverFileIds);

}
