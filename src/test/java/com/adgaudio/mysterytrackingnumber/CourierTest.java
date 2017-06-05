package com.adgaudio.mysterytrackingnumber;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public abstract class CourierTest {

    public abstract List<String> getValidTrackingNumbers();
    public abstract List<String> getInvalidTrackingNumbers();
    public abstract Courier getCourierTestInstance();
    
    @Test
    public void testValidTrackingNumbers() {
        Courier courier = getCourierTestInstance();
        for (String trackingNumber : getValidTrackingNumbers()) {
            assertTrue("This Tracking Number should be valid: " + trackingNumber,
                    courier.isTrackingNumberValid(trackingNumber));
        }
    }

    @Test
    public void testInvalidTrackingNumbers() {
        Courier courier = getCourierTestInstance();
        for (String trackingNumber : getInvalidTrackingNumbers()) {
            assertFalse("This should be an invalid Tracking Number: " + trackingNumber,
                    courier.isTrackingNumberValid(trackingNumber));
        }
    }
    
    @Test
    public void testGetTrackingUrl() {
        Courier courier = getCourierTestInstance();
        assertNotEquals(courier.getTrackingUrlFormatter(), courier.getTrackingUrl("test"));
    }

}
