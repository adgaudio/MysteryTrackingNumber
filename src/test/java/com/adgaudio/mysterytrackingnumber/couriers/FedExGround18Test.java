package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.Arrays;
import java.util.List;

import com.adgaudio.mysterytrackingnumber.Courier;
import com.adgaudio.mysterytrackingnumber.CourierTest;
import com.adgaudio.mysterytrackingnumber.couriers.FedExGround18;


public class FedExGround18Test extends CourierTest {

    @Override
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList("000123450000000027");
    }

    @Override
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList("000000000000000001");
    }

    @Override
    public Courier getCourierTestInstance() {
        return new FedExGround18();
    }

}
