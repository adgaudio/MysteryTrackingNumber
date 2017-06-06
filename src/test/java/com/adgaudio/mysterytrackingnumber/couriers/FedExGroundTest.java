package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.Arrays;
import java.util.List;

import com.adgaudio.mysterytrackingnumber.Courier;
import com.adgaudio.mysterytrackingnumber.CourierTest;
import com.adgaudio.mysterytrackingnumber.couriers.FedExGround;


public class FedExGroundTest extends CourierTest {

    @Override
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList(
            "041441760228964","568283610012000","568283610012734"
            );
    }

    @Override
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList("000000000000001");
    }

    @Override
    public Courier getCourierTestInstance() {
        return new FedExGround();
    }

}
