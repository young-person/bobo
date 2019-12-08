package com.app.crawler.riches.pojo;

import com.app.crawler.pojo.Property;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Bean {
	@XmlAttribute(name = "name")
	private String name;
	@XmlAttribute(name = "mark")
	private String mark;
	@XmlAttribute(name = "code")
	private String code;
	/**
	 * 是抄底  还是待涨  默认为0 抄底  1为待涨
	 */
	@XmlAttribute(name = "macd")
	private String macd = "0";

	@XmlElement(name = "property")
	private List<Property> properties;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMacd() {
		return macd;
	}

	public void setMacd(String macd) {
		this.macd = macd;
	}
}
