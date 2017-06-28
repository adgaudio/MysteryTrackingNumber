package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

class TrackingNumberParser {
    public final String trackingNumber;
    protected Matcher match;
    private final List<CourierJson> couriers = CourierJson.fetchCouriers();
    private final Map<String, CourierS10Json> couriersS10 = CourierS10Json.fetchCouriers();
    public CourierBase courier;

    public TrackingNumberParser(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        for (CourierBase courier : couriers) {
            match = courier.regex.matcher(trackingNumber);
            if (match.matches()) {
                if (match.groupCount() < 2) {
                    throw new RuntimeException(
                            "Code Error: the regex for your courier must include at least two capturing groups");
                }
                ArrayList<Integer> arr = courier.serialNumberParser.apply(match.group("SerialNumber"));
                if (courier.name.contains("martPost")) {
                    System.out.println(courier.name);
                    System.out.println(match.matches());
                    System.out.println(match.group("SerialNumber"));
                    System.out.println(arr);
                }
                
                int checkDigit = Integer.parseInt(match.group("CheckDigit"));

                if (courier.checkDigitAlgo.apply(arr, checkDigit)) {
                    if (courier.name.equals("S10")) {
                        this.courier = couriersS10.get(match.group("CountryCode"));
                    } else if (this.courier == null) {
                        this.courier = courier;
                    }
                    break;
                    // NOTE: multiple couriers might match a single tracking number.
                    // In this case, preference S10, otherwise just use last one.
                }
            }
        }
        if (this.courier == null)
            this.courier = new UnrecognizedCourier();
        
    }
}