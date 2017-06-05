package com.adgaudio.mysterytrackingnumber;

class FedExGround18 extends FedExBase {

    @Override
    protected String getTrackingNumberRegex() {
      return "^[0-9]{2,2}([0-9]{15,15})([0-9])$"; }
    @Override
    public String getName() { return "FedEx Ground 18"; }
}
