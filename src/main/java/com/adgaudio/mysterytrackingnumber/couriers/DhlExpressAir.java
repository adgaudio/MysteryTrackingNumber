package com.adgaudio.mysterytrackingnumber.couriers;

public class DhlExpressAir extends DhlBase {
    @Override
    protected String getTrackingNumberRegex() {
        return "^([0-9]{10,10})([0-9])$";
    }
    
    @Override
    public String getName() {
        return "DHL Express Air";
    }
}
