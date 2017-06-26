package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms;

public class FedExGround18 extends FedExBase {

    @Override
    protected String getTrackingNumberRegex() {
        return "^\\d{2,2}(\\d{15,15})(\\d)$";
    }

    @Override
    public String getName() {
        return "FedEx Ground 18";
    }
    
    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> arr, int checkDigit) {
        return CheckDigitAlgorithms.mod10(arr, checkDigit, 3, 1);
    }
}
