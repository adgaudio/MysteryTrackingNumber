package com.adgaudio.mysterytrackingnumber;

import java.util.Arrays;
import java.util.List;


public class Usps13Test extends CourierTest {

    @Override
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList(
            "RB123456785US"
            );
    }

    @Override
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList("RB123456786US");
    }

    @Override
    public Courier getCourierTestInstance() {
        return new Usps13();
    }

}
