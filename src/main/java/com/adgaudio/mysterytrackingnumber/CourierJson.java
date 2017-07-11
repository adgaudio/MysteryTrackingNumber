package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;
import java.util.List;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.CheckDigitAlgo;
import com.adgaudio.mysterytrackingnumber.SerialNumberParsers.SerialNumberParser;
import com.google.code.regexp.Pattern;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CourierJson extends CourierBase {

    public CourierJson(String name, String trackingUrl, Pattern regex, CheckDigitAlgo checkDigitAlgo,
            SerialNumberParser serialNumberParser) {
        super(name, trackingUrl, regex, checkDigitAlgo, serialNumberParser);
    }

    protected static List<CourierJson> fetchCouriers() {
        String filepathCouriersJson = "/couriers.json";
        return fetchCouriers(filepathCouriersJson);
    }

    protected static List<CourierJson> fetchCouriers(String filepath) {
        JsonObject couriersObj = new JsonParser().parse(openFile(filepath)).getAsJsonObject();
        CourierJsonData[] couriersJson = gson.fromJson(couriersObj.getAsJsonArray("couriers"), CourierJsonData[].class);
        ArrayList<CourierJson> couriers = new ArrayList<>(couriersJson.length);
        for (CourierJsonData courier : couriersJson) {
            couriers.add(new CourierJson(courier.name, courier.tracking_url, parseRegex(courier.regex),
                    parseCheckDigitAlgo(courier.check_digit_algo),
                    parseSerialNumberParser(courier.serial_number_parser)));
        }
        return couriers;
    }
}
