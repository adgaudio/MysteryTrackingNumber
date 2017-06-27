package com.adgaudio.mysterytrackingnumber;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class Courier2 {

    public String name;
    public String tracking_url;

    // TODO: these can be string or list
    // private Pattern regex;
    // TODO: values can have anything. do I need a class per algorithm?
    // private HashMap<String, String> check_digit_algo;

    private String _inherits;
    private static Gson gson = new Gson();

    public Courier2() {
    }

    @Override
    public String toString() {
        return name;
    }

    private static List<Courier2> fetchCouriers() {
        // Fetch and parse json file
        String filename = "tracking_number_data/couriers.json";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JsonObject couriersObj = new JsonParser().parse(br).getAsJsonObject();

        Map<String, Courier2> inheritsMap = gson.fromJson(couriersObj.getAsJsonObject("_shared_courier_data"),
                new TypeToken<Map<String, Courier2>>() {
                }.getType());

        ArrayList<Courier2> couriers = new ArrayList<>(
                Arrays.asList(gson.fromJson(couriersObj.getAsJsonArray("couriers"), Courier2[].class)));

        for (Courier2 courier : couriers) {
            if (courier._inherits != null) {
                Courier2 parent = inheritsMap.get(courier._inherits);
                // if (courier.regex == null)
                // courier.regex = parent.regex;
                // if (courier.check_digit_algo == null)
                // courier.check_digit_algo = parent.check_digit_algo;
                if (courier.tracking_url == null) {
                    courier.tracking_url = parent.tracking_url;
                }
            }
        }
        return couriers;
    }

    public static void main(String[] args) {
        List<Courier2> x = fetchCouriers();
        System.out.println(x);
    }
}
