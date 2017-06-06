package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms;
import com.adgaudio.mysterytrackingnumber.Courier;

public class Ups extends Courier {

    @Override
    protected String getTrackingNumberRegex() {
        return "^1Z(\\w{15,15})(\\w)$";
    }

    @Override
    public String getName() {
        return "Ups";
    }

    @Override
    protected String getTrackingUrlFormatter() {
        return "https://wwwapps.ups.com/WebTracking/track?track=yes&trackNums=%s";
    }
    
    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> seq, int checkDigit) {
        return CheckDigitAlgorithms.mod10(seq, checkDigit, 2, true);
    }
}
