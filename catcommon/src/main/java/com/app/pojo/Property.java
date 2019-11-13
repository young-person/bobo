package com.app.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class Property implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2010384545646581010L;

	@XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "value")
    private String value;

    @XmlAttribute(name = "mark")
    private String mark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
