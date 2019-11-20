package com.app.crawler.riches.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="data")
public class Data {
    @XmlAttribute(name="scan")
    private Boolean scan = false;
    @XmlAttribute(name="scanPeriod")
    private Integer scanPeriod;
    
    @XmlElement(name = "bean")
	private List<Bean> beans;

	public List<Bean> getBeans() {
		return beans;
	}

	public void setBeans(List<Bean> beans) {
		this.beans = beans;
	}
	
}
