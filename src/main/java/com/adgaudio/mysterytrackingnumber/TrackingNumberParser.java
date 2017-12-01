package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;

class TrackingNumberParser {
    private static List<CourierBase> couriers = CourierBase.fetchCouriers();

    public static List<CourierBase> parse(String trackingNumber) {
        List<CourierBase> matchingCouriers = new ArrayList<>();
        Matcher match;

        for (CourierBase courier : couriers) {
            Matcher tmpmatch = courier.regex.matcher(trackingNumber);

            if (tmpmatch.matches()) {
                match = tmpmatch;
                if (match.groupCount() < 2) {
                    throw new RuntimeException(
                            "Code Error: the regex for your courier must include at least two capturing groups");
                }

                ArrayList<Integer> arr = courier.serialNumberParser.apply(match.group("SerialNumber").replaceAll(" ",  ""));   
                Integer checkDigit;
                if (courier.regex.groupNames().contains("CheckDigit")) {
                	checkDigit = Integer.parseInt(match.group("CheckDigit").replaceAll(" ", ""));
                } else {
                	checkDigit = -1;
                }
                if (courier.checkDigitAlgo.apply(arr, checkDigit)) {
                	CourierBase tmp = processAdditionalValidation(courier, match);
                	if (tmp != null) {
                		matchingCouriers.add(tmp);
                	}
                }
            }
        }
        return matchingCouriers;
    }

	private static CourierBase processAdditionalValidation(CourierBase courier, Matcher match) {
        if (courier.additionalValidation == null || courier.additionalValidation.exists == null)
        	return courier;
        Map<String, String> data = null;
        for (String keyName : courier.additionalValidation.exists) {
	        data = courier.additional
	        		.get(keyName).lookup.get(match.group(courier.additional.get(keyName).regexGroupName).replaceAll("\\s+",  ""));
        }
        if (data == null)
        	return null;

        CourierBase rv;
        if (courier.name.equals("S10")) {
        	rv = new CourierBase(
        			data.get("courier") != null ? data.get("courier") : data.get("country"),
        			courier.name,
					data.get("courier_url"),
					Pattern.compile(courier.regex.standardPattern().replaceAll(
							"(?<CountryCode>([A-Z]\\s*){2})", String.format("(?<CountryCode>(%s)", data.get("matches")))),
					courier.checkDigitAlgo,
					courier.serialNumberParser,
					courier.additionalValidation,
					courier.additional);
        } else {
        	rv = courier;
        }
        return rv;		
	}
}