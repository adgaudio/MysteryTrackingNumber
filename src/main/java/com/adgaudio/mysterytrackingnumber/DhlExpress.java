package com.adgaudio.mysterytrackingnumber;

public class DhlExpress extends DhlBase {

    @Override
    protected String getTrackingNumberRegex() {
        return "^([0-9]{9,9})([0-9])$";
    }

    @Override
    public String getName() {
        return "DHL Express";
    }

}
