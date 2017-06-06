package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

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
        int[] seq2 = { 8, 6, 4, 2, 3, 5, 9, 7 };
        int x = 0;
        int i = 0;
        for (int v : seq1) {
            x += v * seq2[i];
            i += 1;
        }
        int r = x % 11;
        if (r == 1
                ) {
            r = 0;
        } else if (r == 0) {
            r = 5;
        } else {
            r = 11 - r;
        }
        return r == checkDigit;
    }
}
