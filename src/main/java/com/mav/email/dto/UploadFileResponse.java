package com.mav.email.dto;

import java.io.Serializable;

public class UploadFileResponse implements Serializable {

	private static final long serialVersionUID = -2278955214729461901L;
	private String fileName;
	private String serverFileId;

	public UploadFileResponse() {
	}

	public UploadFileResponse(String fileName, String serverFileId) {
		super();
		this.fileName = fileName;
		this.serverFileId = serverFileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getServerFileId() {
		return serverFileId;
	}

	public void setServerFileId(String serverFileId) {
		this.serverFileId = serverFileId;
	}

}
