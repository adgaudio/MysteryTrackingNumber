package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.Arrays;
import java.util.List;

import com.adgaudio.mysterytrackingnumber.Courier;
import com.adgaudio.mysterytrackingnumber.CourierTest;
import com.adgaudio.mysterytrackingnumber.couriers.Usps20;


public class Usps20Test extends CourierTest {

    @Override
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList(
            "03071790000523483741"
            );
    }

    @Override
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList(
            "03071790000523483742"
            );
    }

    @Override
    public Courier getCourierTestInstance() {
        return new Usps20();
    }

}
