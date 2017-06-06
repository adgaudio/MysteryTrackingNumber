package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms;
import com.adgaudio.mysterytrackingnumber.Courier;

public class OnTrac extends Courier {

    @Override
    protected String getTrackingNumberRegex() {
        return "^(C[0-9]{13,13})([0-9])$";
    }

    @Override
    public String getName() {
        return "OnTrac";
    }

    @Override
    protected String getTrackingUrlFormatter() {
        return "http://www.ontrac.com/trackingres.asp?tracking_number=%s";
    }

    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> seq, int checkDigit) {
        return CheckDigitAlgorithms.mod10(seq, checkDigit, 2, true);
    }
}
