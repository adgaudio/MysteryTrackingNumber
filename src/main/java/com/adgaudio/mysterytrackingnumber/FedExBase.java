package com.adgaudio.mysterytrackingnumber;

public abstract class FedExBase extends Courier {
    @Override
    protected String getTrackingUrlFormatter() {
        return "https://www.fedex.com/apps/fedextrack/?tracknumbers=%s";
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
