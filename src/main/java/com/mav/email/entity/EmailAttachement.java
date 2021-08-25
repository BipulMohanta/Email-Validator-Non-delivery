package com.mav.email.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EMAIL_ATTACHEMENT")
public class EmailAttachement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ATTACHEMENT_ID", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "DOCUMENT_ID")
	private long documentId;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "FILE_EXTENSION")
	private String fileExtension;

	@Column(name = "FILE_SIZE")
	private long fileSize;

	@Column(name = "FILE_UPLOAD_DATE")
	private Date fileUploadedOnDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private SentMailDetails sentMailDetails;

	public EmailAttachement() {

	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public Date getFileUploadedOnDate() {
		return fileUploadedOnDate;
	}

	public void setFileUploadedOnDate(Date fileUploadedOnDate) {
		this.fileUploadedOnDate = fileUploadedOnDate;
	}

}
