package com.adgaudio.mysterytrackingnumber;

import java.util.regex.Matcher;

public abstract class DhlBase extends Courier {

    @Override
    protected String getTrackingUrlFormatter() {
        return "http://www.dhl.com/en/express/tracking.html?brand=DHL&AWB=%s";
    }

    @Override
    protected Boolean hasValidCheckDigit(String trackingNumber) {
        Matcher m = super.regex.matcher(trackingNumber);
        m.matches(); m.groupCount();  // java bug requires both of these to be called?!
        Long x = Long.parseUnsignedLong(m.group(1));
        String checkDigit = m.group(2);
        return x % 7 == Integer.parseInt(checkDigit);
    }

}
