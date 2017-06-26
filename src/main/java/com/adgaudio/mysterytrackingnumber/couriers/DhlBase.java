package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms;
import com.adgaudio.mysterytrackingnumber.Courier;

public abstract class DhlBase extends Courier {

    @Override
    protected String getTrackingUrlFormatter() {
        return "http://www.dhl.com/en/express/tracking.html?brand=DHL&AWB=%s";
    }

    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> arr, int checkDigit) {
        return CheckDigitAlgorithms.mod7(arr, checkDigit);
    }

}
