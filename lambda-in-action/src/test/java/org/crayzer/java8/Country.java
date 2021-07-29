package org.crayzer.java8;

/**
 * @author crayzer
 */
public class Country {

    private String isocode;

    public String getIsocode() {
        return isocode;
    }

    public void setIsocode(String isocode) {
        this.isocode = isocode;
    }

    @Override
    public String toString() {
        return "Country{" +
                "isocode=" + isocode +
                '}';
    }
}
