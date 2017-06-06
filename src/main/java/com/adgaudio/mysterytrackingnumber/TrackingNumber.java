package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.adgaudio.mysterytrackingnumber.couriers.DhlExpress;
import com.adgaudio.mysterytrackingnumber.couriers.DhlExpressAir;
import com.adgaudio.mysterytrackingnumber.couriers.FedExExpress;
import com.adgaudio.mysterytrackingnumber.couriers.FedExGround;
import com.adgaudio.mysterytrackingnumber.couriers.FedExGround18;
import com.adgaudio.mysterytrackingnumber.couriers.FedExGround96;
import com.adgaudio.mysterytrackingnumber.couriers.FedExSmartPost;
import com.adgaudio.mysterytrackingnumber.couriers.OnTrac;
import com.adgaudio.mysterytrackingnumber.couriers.Ups;
import com.adgaudio.mysterytrackingnumber.couriers.Usps13;
import com.adgaudio.mysterytrackingnumber.couriers.Usps20;
import com.adgaudio.mysterytrackingnumber.couriers.Usps91;

public class TrackingNumber {
    protected Courier courier;
    public String trackingNumber;

    private static List<Courier> couriers = Arrays.asList(
            new DhlExpressAir(), new DhlExpress(), new FedExExpress(),
            new FedExGround(), new FedExGround18(), new FedExGround96(), new FedExSmartPost(),
            new OnTrac(),
            new Ups(),
            new Usps13(), new Usps20(), new Usps91());

    public TrackingNumber(String trackingNumber, Courier courier) {
        this.trackingNumber = trackingNumber;
        this.courier = courier;
    }

    public static TrackingNumber parse(String trackingNumber) {
        for (Courier c : couriers) {
            if (c.isTrackingNumberValid(trackingNumber)) {
                return new TrackingNumber(trackingNumber, c); // note: possible
                                                              // issue if
                                                              // multiple
                                                              // couriers match
                                                              // the same number
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

    public String getCourierName() {
        return courier.getName();
    }
    
    public String getTrackingUrl() {
        return courier.getTrackingUrl(trackingNumber);
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
