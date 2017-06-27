package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

class TrackingNumberParser {
    public final String trackingNumber;
    private Matcher match;
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
                ArrayList<Integer> arr = parseCheckDigitSequence(match.group("SerialNumber"));
                int checkDigit = Integer.parseInt(match.group("CheckDigit"));
                if (courier.checkDigitAlgo.apply(arr, checkDigit)) {
                    if (courier.name.equals("S10")) {
                        this.courier = couriersS10.get(match.group("CountryCode"));
                    } else if (this.courier == null) {
                        this.courier = courier;
                    }
                    // NOTE: multiple couriers might match a single tracking number.
                    // In this case, preference S10, otherwise just use last one.
                }
            }
        }
        if (this.courier == null)
            this.courier = new UnrecognizedCourier();
    }

    /*
     * The default helper method for Couriers used as a pre-processing step to
     * evaluate hasValidCheckDigit. This method assumes the check digit sequence
     * is a string of digits, and just converts each digit into its literal
     * numeric value.
     */
    protected ArrayList<Integer> parseCheckDigitSequence(String capturedRegexGroup) {
        ArrayList<Integer> arr = new ArrayList<>(capturedRegexGroup.length() + 1);
        for (char c : capturedRegexGroup.toCharArray()) {
            arr.add(Character.isDigit(c) ? Character.getNumericValue(c) : (((int) c) - 3) % 10);
        }
        return arr;
    }
}