package com.adgaudio.mysterytrackingnumber;

import java.util.regex.Matcher;

public class FedExExpress extends FedExBase {

    @Override
    protected String getTrackingNumberRegex() {
        return "^([0-9]{11,11})([0-9])$";
    }

    @Override
    public String getName() {
        return "FedEx Express";
    }

    @Override
    protected Boolean hasValidCheckDigit(String trackingNumber) {
        Matcher m = super.regex.matcher(trackingNumber);
        if (!m.matches()) {
            return false;
        }
        char[] seq1 = m.group(1).toCharArray();
        int[] seq2 = {3,1,7,3,1,7,3,1,7,3,1};
        int total = 0;
        for (int i=0; i<seq2.length; i++) {
            total += Character.getNumericValue(seq1[i]) * seq2[i];
        }
        return total % 11 % 10 == Integer.parseInt(m.group(2));
    }
}
