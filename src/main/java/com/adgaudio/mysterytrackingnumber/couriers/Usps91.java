package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms;

public class Usps91 extends UspsBase {
    @Override
    protected String getTrackingNumberRegex() {
        return "^(?:420\\d{5})?(9[1-5](?:[0-9]{19}|[0-9]{23}))([0-9])$";
    }

    @Override
    public String getName() {
        return "USPS 91";
    }

    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> arr, int checkDigit) {
        return CheckDigitAlgorithms.mod10(arr, checkDigit, 3, 1);
    }
}
