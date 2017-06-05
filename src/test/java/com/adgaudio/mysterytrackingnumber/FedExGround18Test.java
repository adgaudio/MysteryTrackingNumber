package com.adgaudio.mysterytrackingnumber;

import java.util.Arrays;
import java.util.List;


public class FedExGround18Test extends CourierTest {

    @Override
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList("000123450000000027");
    }

    @Override
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList("000000000000000000");
    }

    @Override
    public Courier getCourierTestInstance() {
        return new FedExGround18();
    }

}
