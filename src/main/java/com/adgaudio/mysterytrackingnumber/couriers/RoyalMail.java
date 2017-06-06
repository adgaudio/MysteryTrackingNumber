package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms;
import com.adgaudio.mysterytrackingnumber.Courier;

public class RoyalMail extends Courier {
    // http://www.royalmail.com/sites/default/files/COSS_Spec_03_-_Barcodes_and_Tracking_Numbers_v1_1.pdf

    @Override
    public String getName() {
        return "Royal Mail";
    }

    @Override
    protected String getTrackingNumberRegex() {
        return "^(?:[A-Z]{2,2})(\\d{8})(\\d)(?:[A-Z]{2})$";
    }

    @Override
    protected String getTrackingUrlFormatter() {
        return "https://www.royalmail.com/track-your-item?trackNumber=%s";
    }

    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> seq1, int checkDigit) {
        return CheckDigitAlgorithms.sumOfProductsWithWeightingsMod11(seq1, checkDigit);
    }

}
