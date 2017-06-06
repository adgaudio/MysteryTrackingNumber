package com.adgaudio.mysterytrackingnumber.couriers;

public class FedExGround18 extends FedExBase {

    @Override
    protected String getTrackingNumberRegex() {
        return "^\\d{2,2}(\\d{15,15})(\\d)$";
    }

    @Override
    public String getName() {
        return "FedEx Ground 18";
    }
}
