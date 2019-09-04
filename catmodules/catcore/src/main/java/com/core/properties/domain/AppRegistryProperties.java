package com.core.properties.domain;

import com.core.properties.CatConfigurationPropertySource;

public class AppRegistryProperties  extends CatConfigurationPropertySource<AppRegistryProperties> {

	private String registryService;
	
	private String registryId;
	
	private String registryProducer;
	
	private String registryConsumer;
	
	private String seq;

	public String getRegistryService() {
		return registryService;
	}

	public void setRegistryService(String registryService) {
		this.registryService = registryService;
	}

	public String getRegistryId() {
		return registryId;
	}

	public void setRegistryId(String registryId) {
		this.registryId = registryId;
	}

	public String getRegistryProducer() {
		return registryProducer;
	}

	public void setRegistryProducer(String registryProducer) {
		this.registryProducer = registryProducer;
	}

	public String getRegistryConsumer() {
		return registryConsumer;
	}

	public void setRegistryConsumer(String registryConsumer) {
		this.registryConsumer = registryConsumer;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	
}
