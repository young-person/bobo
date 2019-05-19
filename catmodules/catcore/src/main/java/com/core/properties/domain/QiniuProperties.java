package com.core.properties.domain;

import com.core.properties.CatConfigurationPropertySource;

public class QiniuProperties extends CatConfigurationPropertySource<QiniuProperties> {

	private String accessKey;
	private String secretKey;
	private String privateHost;
	private String publicHost;
	private Long fileMaxSize;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getPrivateHost() {
		return privateHost;
	}

	public void setPrivateHost(String privateHost) {
		this.privateHost = privateHost;
	}

	public String getPublicHost() {
		return publicHost;
	}

	public void setPublicHost(String publicHost) {
		this.publicHost = publicHost;
	}

	public Long getFileMaxSize() {
		return fileMaxSize;
	}

	public void setFileMaxSize(Long fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}
}
