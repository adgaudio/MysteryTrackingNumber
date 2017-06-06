package com.adgaudio.mysterytrackingnumber.couriers;

public class FedExGround extends FedExBase {

    @Override
    protected String getTrackingNumberRegex() { return "^(\\d{14,14})(\\d)$"; }
    @Override
    public String getName() { return "FedEx Ground"; }
}
