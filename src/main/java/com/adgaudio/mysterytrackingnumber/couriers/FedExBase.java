package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms;
import com.adgaudio.mysterytrackingnumber.Courier;

public abstract class FedExBase extends Courier {
    @Override
    protected String getTrackingUrlFormatter() {
        return "https://www.fedex.com/apps/fedextrack/?tracknumbers=%s";
    }

    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> arr, int checkDigit) {
        return CheckDigitAlgorithms.mod10(arr, checkDigit, 3, arr.size() % 2 == 0);
    }
}
