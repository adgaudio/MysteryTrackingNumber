package com.adgaudio.mysterytrackingnumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DhlExpressAir extends Courier {
    protected Pattern getTrackingNumberRegex() { return Pattern.compile("^([0-9]{10,10})([0-9])$"); }
    public String getName() { return "dhl express air"; }
    protected String getTrackingUrlFormatter() { return "http://www.dhl.com/en/express/tracking.html?brand=DHL&AWB=%s"; }
    protected Boolean hasValidCheckDigit(String trackingNumber) {
        Matcher m = super.regex.matcher(trackingNumber);
        String checkDigit = m.group(1);
        return new Integer(m.group(0)) % 7 == new Integer(checkDigit);
    }
}
