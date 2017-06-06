package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.ArrayList;

import com.adgaudio.mysterytrackingnumber.Courier;

public abstract class DhlBase extends Courier {

    @Override
    protected String getTrackingUrlFormatter() {
        return "http://www.dhl.com/en/express/tracking.html?brand=DHL&AWB=%s";
    }

    @Override
    protected Boolean hasValidCheckDigit(ArrayList<Integer> arr, int checkDigit) {
        StringBuilder strNum = new StringBuilder();
        for (int num : arr) 
             strNum.append(num);
        Long x = Long.parseUnsignedLong(strNum.toString());
        return x % 7 == checkDigit;
    }

}
