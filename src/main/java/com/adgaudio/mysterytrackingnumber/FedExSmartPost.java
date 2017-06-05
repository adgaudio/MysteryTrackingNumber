package com.adgaudio.mysterytrackingnumber;

public class FedExSmartPost extends FedExBase {

    @Override
    public String getName() {
        return "FedEx SmartPost";
    }

    @Override
    protected String getTrackingNumberRegex() {
        return "^((?:92)?[0-9]{5}[0-9]{14})([0-9])$";
    }

    @Override
    protected Boolean hasValidCheckDigit(String trackingNumber) {
        // http://stackoverflow.com/questions/15744704/how-to-calculate-a-fedex-smartpost-tracking-number-check-digit
        if (!trackingNumber.startsWith("92")) {
            trackingNumber = "92" + trackingNumber;
        }
        char[] arr = trackingNumber.toCharArray();
        int checkDigit = Character.getNumericValue(arr[arr.length-1]);
        int x = 0;
        for (int i=arr.length-2; i>=0; i--) {
            int v = Character.getNumericValue(arr[i]);
            x += (i % 2 == 0) ? 3*v : v;
        }
        x = x % 10;
        if (x != 0) {
            x = 10 - x;
        }
        return x == checkDigit;
    }
}
