package com.adgaudio.mysterytrackingnumber;

import java.util.Arrays;
import java.util.List;

public class DhlExpressTest extends CourierTest {

    
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList("3318810025", "8487135506", "3318810036", "3318810014");
    }
    
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList("a", "1", "1111111111");
    }

    public Courier getCourierTestInstance() {
        return new DhlExpress();
    }
}
