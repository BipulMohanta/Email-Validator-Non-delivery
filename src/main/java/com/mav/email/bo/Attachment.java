package com.mav.email.bo;
/**
 * 
 * @author bipul.mohanta
 *
 */
public class Attachment {
	private String actualFilePath;
	private long fileSize;
	private String fileName;
	private String fileExtension;

	public String getActualFilePath() {
		return actualFilePath;
	}

	public void setActualFilePath(String actualFilePath) {
		this.actualFilePath = actualFilePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
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

	@Override
	public String toString() {
		return "Attachment [actualFilePath=" + actualFilePath + ", fileSize=" + fileSize + ", fileName=" + fileName
				+ ", fileExtension=" + fileExtension + "]";
	}
	
	
}
