package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms;

public class Usps13 extends UspsBase {
    @Override
    protected String getTrackingNumberRegex() {
        return "^(?:[A-Z]{2,2})([0-9]{8,8})(\\d)(?:[A-Z]{2,2})$";
    }

    @Override
    public String getName() {
        return "USPS13";
    }

    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> seq1, int checkDigit) {
        return CheckDigitAlgorithms.s10_check_digit_algo(seq1, checkDigit);
    }
}
