package com.bobo.domain;



import java.io.Serializable;

public class FileInfo implements Serializable{

	private static final long serialVersionUID = -894759518303934049L;
	private String fileName;
	private String path;
	private String md5;
	private String uploadDate;

	private String fileId;
	private String fileInfo;
	private long fileSize;

	public String getFileName() {
		return fileName;
	}

	public String getPath() {
		return path;
	}

	public String getMd5() {
		return md5;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public String getFileId() {
		return fileId;
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
