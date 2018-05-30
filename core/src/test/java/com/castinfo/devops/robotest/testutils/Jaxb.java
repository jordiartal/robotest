package com.castinfo.devops.robotest.testutils;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement
public class Jaxb {

    String value;

    public String getValue() {
        return this.value;
    }

    @XmlValue
    public void setValue(final String codigo) {
        this.value = codigo;
    }

}
