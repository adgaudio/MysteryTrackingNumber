package com.adgaudio.mysterytrackingnumber;

import java.util.Arrays;
import java.util.List;


public class OnTracTest extends CourierTest {

    @Override
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList("C11031500001879", "C10999911320231");
    }

    @Override
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList("C10000000000000", "C11031500001889");
    }

    @Override
    public Courier getCourierTestInstance() {
        return new OnTrac();
    }

}
