package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.Arrays;
import java.util.List;

import com.adgaudio.mysterytrackingnumber.Courier;
import com.adgaudio.mysterytrackingnumber.CourierTest;
import com.adgaudio.mysterytrackingnumber.couriers.DhlExpressAir;

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
