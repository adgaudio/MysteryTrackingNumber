package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;
import java.util.Arrays;

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
    protected Boolean hasValidCheckDigit(ArrayList<Integer> arr, int checkDigit) {
        ArrayList<Integer> code = new ArrayList<Integer>(Arrays.asList(9, 2));
        //     http://stackoverflow.com/questions/15744704/how-to-calculate-a-fedex-smartpost-tracking-number-check-digit
        if (!arr.subList(0, 2).equals(code)) {
            arr.addAll(0, code);
        }
        return super.hasValidCheckDigit(arr, checkDigit);
    }

}
