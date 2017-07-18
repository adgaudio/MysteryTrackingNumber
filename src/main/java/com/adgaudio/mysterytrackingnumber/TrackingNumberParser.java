package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.code.regexp.Matcher;

class TrackingNumberParser {
    public final String trackingNumber;
    protected Matcher match;
    private static List<CourierBase> couriers = CourierBase.fetchCouriers();
    private static Map<String, CourierBase> couriersS10 = null;
    public CourierBase courier;

    public TrackingNumberParser(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        for (CourierBase courier : couriers) {
            Matcher tmpmatch = courier.regex.matcher(trackingNumber);

            if (tmpmatch.matches()) {
                match = tmpmatch;
                if (match.groupCount() < 2) {
                    throw new RuntimeException(
                            "Code Error: the regex for your courier must include at least two capturing groups");
                }

                ArrayList<Integer> arr = courier.serialNumberParser.apply(match.group("SerialNumber"));                
                int checkDigit = Integer.parseInt(match.group("CheckDigit"));
                if (courier.checkDigitAlgo.apply(arr, checkDigit)) {
                    if (courier.name.equals("S10")) {
                    	if (couriersS10 == null) {
                    		couriersS10 = CourierS10Json.fetchCouriers(courier);
                    	}
                        this.courier = couriersS10.get(match.group("CountryCode"));
                    } else if (this.courier == null) {
                        this.courier = courier;
                    }
                    if (this.courier != null)
                        break;
                    // NOTE: multiple couriers might match a single tracking number.
                }
            }
        }
        if (this.courier == null)
            this.courier = new UnrecognizedCourier();
        
    }
}