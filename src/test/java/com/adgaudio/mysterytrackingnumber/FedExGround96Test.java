package com.adgaudio.mysterytrackingnumber;

import java.util.Arrays;
import java.util.List;


public class FedExGround96Test extends CourierTest {

    @Override
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList("9611020987654312345672");
    }

    @Override
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList("9600000000000000000000");
    }

    @Override
    public Courier getCourierTestInstance() {
        return new FedExGround96();
    }

}
