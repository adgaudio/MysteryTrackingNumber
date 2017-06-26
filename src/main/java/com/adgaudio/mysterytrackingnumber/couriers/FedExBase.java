package com.adgaudio.mysterytrackingnumber.couriers;

import com.adgaudio.mysterytrackingnumber.Courier;

public abstract class FedExBase extends Courier {
    @Override
    protected String getTrackingUrlFormatter() {
        return "https://www.fedex.com/apps/fedextrack/?tracknumbers=%s";
    }
}
