package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms;

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
        int[] weightings = {3,1,7,3,1,7,3,1,7,3,1};
        return CheckDigitAlgorithms.sumProductWithWeightingsAndModulo(seq1, checkDigit, weightings, 11, 10);
    }
}
