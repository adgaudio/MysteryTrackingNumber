package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms;

public class FedExGround extends FedExBase {

    @Override
    protected String getTrackingNumberRegex() { return "^(\\d{14,14})(\\d)$"; }
    @Override
    public String getName() { return "FedEx Ground"; }

    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> arr, int checkDigit) {
        return CheckDigitAlgorithms.mod10(arr, checkDigit, 1, 3);
    }
}
