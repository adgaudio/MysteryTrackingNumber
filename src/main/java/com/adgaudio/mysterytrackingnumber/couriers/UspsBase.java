package com.adgaudio.mysterytrackingnumber.couriers;

import com.adgaudio.mysterytrackingnumber.Courier;

public abstract class UspsBase extends Courier {

    @Override
    protected String getTrackingUrlFormatter() {
        return "https://tools.usps.com/go/TrackConfirmAction?tLabels=%s";
    }
}
