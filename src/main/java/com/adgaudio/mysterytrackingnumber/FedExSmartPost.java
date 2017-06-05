package com.adgaudio.mysterytrackingnumber;

public class FedExSmartPost extends FedExBase {

    @Override
    public String getName() {
        return "FedEx SmartPost";
    }

    @Override
    protected String getTrackingNumberRegex() {
        return "^((?:92)?[0-9]{5}[0-9]{14})([0-9])$";
    }

}
