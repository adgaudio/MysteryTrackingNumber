package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.CheckDigitAlgo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class CourierJson extends CourierBase {

    public CourierJson(String name, String trackingUrl, Pattern regex, CheckDigitAlgo checkDigitAlgo) {
        super(name, trackingUrl, regex, checkDigitAlgo);
    }

    protected static List<CourierJson> fetchCouriers() {
        String filepathCouriersJson = "tracking_number_data/couriers.json";
        return fetchCouriers(filepathCouriersJson);
    }
    
    protected static List<CourierJson> fetchCouriers(String filepath) {

        JsonObject couriersObj = new JsonParser().parse(openFile(filepath)).getAsJsonObject();

        Map<String, CourierJsonData> inheritsMap = gson.fromJson(couriersObj.getAsJsonObject("_shared_courier_data"),
                new TypeToken<Map<String, CourierJsonData>>() {
                }.getType());

        CourierJsonData[] couriersJson = gson.fromJson(couriersObj.getAsJsonArray("couriers"), CourierJsonData[].class);

        ArrayList<CourierJson> couriers = new ArrayList<>(couriersJson.length);

        for (CourierJsonData courier : couriersJson) {
            if (courier._inherits != null) {
                CourierJsonData parent = inheritsMap.get(courier._inherits);
                if (courier.regex == null)
                    courier.regex = parent.regex;
                if (courier.check_digit_algo == null)
                    courier.check_digit_algo = parent.check_digit_algo;
                if (courier.tracking_url == null) {
                    courier.tracking_url = parent.tracking_url;
                }
            }
            couriers.add(new CourierJson(
                    courier.name, courier.tracking_url, parseRegex(courier.regex), parseCheckDigitAlgo(courier.check_digit_algo)));
        }
        return couriers;
    }
}
