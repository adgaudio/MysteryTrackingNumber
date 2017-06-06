package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

public class FedExExpress extends FedExBase {

    @Override
    protected String getTrackingNumberRegex() {
        return "^([0-9]{11,11})([0-9])$";
    }

    @Override
    public String getName() {
        return "FedEx Express";
    }

    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> seq1, int checkDigit) {
        int[] seq2 = {3,1,7,3,1,7,3,1,7,3,1};
        int total = 0;
        for (int i=0; i<seq2.length; i++) {
            total += seq1.get(i) * seq2[i];
        }
        return total % 11 % 10 == checkDigit;
    }
}
