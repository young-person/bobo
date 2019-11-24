package com.app.crawler.riches.pojo;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="data")
@XmlAccessorType(XmlAccessType.FIELD)
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
