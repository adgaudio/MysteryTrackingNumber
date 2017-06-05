package com.adgaudio.mysterytrackingnumber;

import java.util.regex.Matcher;

public class Ups extends Courier {

    @Override
    protected String getTrackingNumberRegex() {
        return "^1Z(\\w{15,15})(\\w)$";
    }

    @Override
    public String getName() {
        return "Ups";
    }

    @Override
    protected String getTrackingUrlFormatter() {
        return "https://wwwapps.ups.com/WebTracking/track?track=yes&trackNums=%s";
    }

    @Override
    protected Boolean hasValidCheckDigit(String trackingNumber) {
        Matcher m = super.regex.matcher(trackingNumber);
        m.matches();
        m.groupCount(); // java requires both of these to be called?!

        char[] seq = m.group(1).toCharArray();
        int checkDigit = Integer.parseInt(m.group(2));

        int x = 0;
        for (int i = 0; i < seq.length; i++) {
            int v = Character.isDigit(seq[i]) ? Character.getNumericValue(seq[i]) : (((int) seq[i]) - 3) % 10;
            x += (v % 2 == 1) ? 2 * v : v;
        }
        x = x % 10;
        if (x != 0) {
            x = 10 - x;
        }
        return checkDigit == x;
    }
}
