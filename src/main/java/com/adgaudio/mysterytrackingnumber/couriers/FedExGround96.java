package com.adgaudio.mysterytrackingnumber.couriers;

public class FedExGround96 extends FedExBase {

    @Override
    protected String getTrackingNumberRegex() { return "^96[0-9]{5,5}([0-9]{14,14})([0-9])$"; }
    @Override
    public String getName() { return "FedEx Ground 96"; }

}
