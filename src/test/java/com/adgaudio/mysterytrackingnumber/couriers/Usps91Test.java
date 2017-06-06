package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.Arrays;
import java.util.List;

import com.adgaudio.mysterytrackingnumber.Courier;
import com.adgaudio.mysterytrackingnumber.CourierTest;
import com.adgaudio.mysterytrackingnumber.couriers.Usps91;


public class Usps91Test extends CourierTest {

    @Override
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList(
            "420221539101026837331000039521", "9101026837331000039521"
            );
    }

    @Override
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList(
            "420000000000000000000000000000"
            );
    }

    @Override
    public Courier getCourierTestInstance() {
        return new Usps91();
    }

}
