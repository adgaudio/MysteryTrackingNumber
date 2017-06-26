package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms;

public class FedExGround96 extends FedExBase {

    @Override
    protected String getTrackingNumberRegex() { return "^96[0-9]{5,5}([0-9]{14,14})([0-9])$"; }
    @Override
    public String getName() { return "FedEx Ground 96"; }

    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> arr, int checkDigit) {
        return CheckDigitAlgorithms.mod10(arr, checkDigit, 1, 3);
    }
}
