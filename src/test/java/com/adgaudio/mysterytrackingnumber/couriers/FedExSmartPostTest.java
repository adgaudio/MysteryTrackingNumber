package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.Arrays;
import java.util.List;

import com.adgaudio.mysterytrackingnumber.Courier;
import com.adgaudio.mysterytrackingnumber.CourierTest;
import com.adgaudio.mysterytrackingnumber.couriers.FedExSmartPost;

public class FedExSmartPostTest extends CourierTest {

    @Override
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList("61299998820821171811", "9261292700768711948021");
    }

    @Override
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList("61299998820821171812", "00000000000000000000", "9200000000000000000000");
    }

    @Override
    public Courier getCourierTestInstance() {
        return new FedExSmartPost();
    }
}
