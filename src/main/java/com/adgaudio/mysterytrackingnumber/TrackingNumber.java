package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TrackingNumber {
    public Courier courier;
    public String trackingNumber;
    
    private static List<Courier> couriers = Arrays.asList(
            new DhlExpressAir()
            );
    
    public TrackingNumber(String trackingNumber, Courier courier) {
        this.trackingNumber = trackingNumber;
        this.courier = courier;
    }

    public static TrackingNumber parse(String trackingNumber) {
        for (Courier c : couriers) {
            if (c.isTrackingNumberValid(trackingNumber)) {
                return new TrackingNumber(trackingNumber, c);  // note: possible issue if multiple couriers match the same number
            }
        }
        return null;
    }
    
    public static List<TrackingNumber> filterAndParseTrackingNumbers(Collection<String> barcodes) {
        List<TrackingNumber> res = new ArrayList<>();
        for (String s : barcodes) {
            TrackingNumber t = TrackingNumber.parse(s);
            if (t != null) {
                res.add(t);
            }
        }
        return res;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", this.courier, this.trackingNumber);
    }

    @Override
    public int hashCode() {
        return this.trackingNumber.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TrackingNumber))
            return false;
        if (obj == this)
            return true;
        return this.trackingNumber.equals(((TrackingNumber) obj).trackingNumber);
    }
}
