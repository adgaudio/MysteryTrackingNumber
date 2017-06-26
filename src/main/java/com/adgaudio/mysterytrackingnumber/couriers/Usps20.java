package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms;

public class Usps20 extends UspsBase {
    @Override
    protected String getTrackingNumberRegex() {
        return "^([0-9]{2,2}[0-9]{9,9}[0-9]{8,8})([0-9])$";
    }

    @Override
    public String getName() {
        return "USPS20";
    }

    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> arr, int checkDigit) {
        return CheckDigitAlgorithms.mod10(arr, checkDigit, 3, 1);
    }
}
