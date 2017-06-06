package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.Arrays;
import java.util.List;

import com.adgaudio.mysterytrackingnumber.Courier;
import com.adgaudio.mysterytrackingnumber.CourierTest;

public class RoyalMailTest extends CourierTest {

    @Override
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList("FF070621885GB", "TT222209017GB", "TT327219141GB");
    }

    @Override
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList("TT827210001GB", "TT000000001GB");
    }

    @Override
    public Courier getCourierTestInstance() {
        return new RoyalMail();
    }

}
