package com.adgaudio.mysterytrackingnumber;

public class FedExGround extends FedExBase {

    @Override
    protected String getTrackingNumberRegex() { return "^([0-9]{15,15})$"; }
    @Override
    public String getName() { return "FedEx Ground"; }
}
