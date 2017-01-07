package org.kabeja.entities;

/**
 * Created by Veronika Zwickenpflug on 30.12.2016.
 */
public class XData {

    private final String applicationName;

    private String xDataString;

    public XData(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getXDataString() {
        return xDataString;
    }

    public void setxDataString(String xDataString) {
        this.xDataString = xDataString;
    }

    public String getApplicationName() {
        return applicationName;
    }
}
