package com.app.pojo;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="cat")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cat {
    @XmlAttribute(name="scan")
    private Boolean scan = false;
    @XmlAttribute(name="scanPeriod")
    private Integer scanPeriod;

    @XmlElement(name = "property")
    private List<Property> properties;

    public Boolean getScan() {
        return scan;
    }

    public void setScan(Boolean scan) {
        this.scan = scan;
    }

    public Integer getScanPeriod() {
        return scanPeriod;
    }

    public void setScanPeriod(Integer scanPeriod) {
        this.scanPeriod = scanPeriod;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}
