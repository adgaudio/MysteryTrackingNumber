package com.adgaudio.mysterytrackingnumber;

public abstract class FedExBase extends Courier {
    @Override
    protected String getTrackingUrlFormatter() {
        return "https://www.fedex.com/apps/fedextrack/?tracknumbers=%s";
    }
}
