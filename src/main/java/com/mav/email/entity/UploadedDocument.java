package com.mav.email.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @author bipul.mohanta
 *
 */

@NamedQuery(name="getUploadedDocumentList",query="SELECT upd FROM UploadedDocument upd WHERE upd.documentId IN (:serverFileIds) ")

@Entity
@Table(name = "UPLOADED_DOCUMENT")
public class UploadedDocument {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UPLOADED_DOCUMENT_ID", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "DOCUMENT_ID")
	private String documentId;

	@Column(name = "DOCUMENT_NAME")
	private String documentName;

	@Column(name = "DOCUMENT_EXTENSION")
	private String documentExtension;

	@Column(name = "DOCUMENT_SIZE")
	private long documentSize;

	@Column(name = "DOCUMENT_UPLOAD_DATE")
	private Date documentUploadedOnDate;

	public UploadedDocument() {
	}

	public UploadedDocument(UUID id, String documentId, String documentName, String documentExtension, long documentSize,
			Date documentUploadedOnDate) {
		super();
		this.id = id;
		this.documentId = documentId;
		this.documentName = documentName;
		this.documentExtension = documentExtension;
		this.documentSize = documentSize;
		this.documentUploadedOnDate = documentUploadedOnDate;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentExtension() {
		return documentExtension;
	}

	public void setDocumentExtension(String documentExtension) {
		this.documentExtension = documentExtension;
	}

	public long getDocumentSize() {
		return documentSize;
	}

	public void setDocumentSize(long documentSize) {
		this.documentSize = documentSize;
	}

	public Date getDocumentUploadedOnDate() {
		return documentUploadedOnDate;
	}

	public void setDocumentUploadedOnDate(Date documentUploadedOnDate) {
		this.documentUploadedOnDate = documentUploadedOnDate;
	}

}
