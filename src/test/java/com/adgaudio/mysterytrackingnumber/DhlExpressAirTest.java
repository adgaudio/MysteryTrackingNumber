package com.adgaudio.mysterytrackingnumber;

import java.util.Arrays;
import java.util.List;

public class DhlExpressAirTest extends CourierTest {

    
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList("73891051146");
    }
    
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList("a", "70", "1111111111", "73891051147");
    }

    public Courier getCourierTestInstance() {
        return new DhlExpressAir();
    }
}
