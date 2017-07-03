package com.adgaudio.mysterytrackingnumber;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Pattern;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.CheckDigitAlgo;
import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.Dummy;
import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.Mod10;
import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.Mod7;
import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.S10;
import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.SumProductWithWeightingsAndModulo;
import com.adgaudio.mysterytrackingnumber.SerialNumberParsers.SerialNumberParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CourierBase {
    public final String name;
    public final String trackingUrl;
    protected final Pattern regex;
    protected final CheckDigitAlgo checkDigitAlgo;
    protected final SerialNumberParser serialNumberParser;

    @Override
    public String toString() {
        return name;
    }

    static Gson gson = new Gson();

    public CourierBase(String name, String trackingUrl, Pattern regex, CheckDigitAlgo checkDigitAlgo, SerialNumberParser serialNumberParser) {
        this.name = name;
        this.trackingUrl = trackingUrl;
        this.regex = regex;
        this.checkDigitAlgo = checkDigitAlgo;
        this.serialNumberParser = serialNumberParser;
    }

    /* Internal class used for parsing JSON files */
    class CourierJsonData {
        public String name;
        public String tracking_url;
        public JsonElement regex;
        public JsonObject serial_number_parser;
        public JsonObject check_digit_algo;
        String _inherits;
    }

    static BufferedReader openFile(String filepath) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filepath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return br;
    }

    /* Standard way regular expressions are parsed in our JSON data */
    static Pattern parseRegex(JsonElement regex) {
        String tmpRegex;
        if (regex.isJsonArray()) {
            tmpRegex = String.join("", gson.fromJson(regex, String[].class));
        } else {
            tmpRegex = regex.getAsString();
        }
        return Pattern.compile(tmpRegex.replaceAll("\\(\\?P<", "\\(\\?<"));
    }

    /*
     * Map check digit parameters defined in JSON data to check digit
     * algorithms.
     */
    static CheckDigitAlgo parseCheckDigitAlgo(JsonObject check_digit_algo) {
        CheckDigitAlgo checkDigitAlgo = null;
        switch (check_digit_algo.get("name").getAsString().toLowerCase()) {
        case "mod10":
            checkDigitAlgo = new Mod10(check_digit_algo.get("evensMultiplier").getAsInt(),
                    check_digit_algo.get("oddsMultiplier").getAsInt());
            break;
        case "mod7":
            checkDigitAlgo = new Mod7();
            break;
        case "s10":
            checkDigitAlgo = new S10();
            break;
        case "sum_product_with_weightings_and_modulo":
            checkDigitAlgo = new SumProductWithWeightingsAndModulo(
                    gson.fromJson(check_digit_algo.get("weightings").getAsJsonArray(), int[].class),
                    check_digit_algo.get("modulo1").getAsInt(), check_digit_algo.get("modulo2").getAsInt());
            break;
        case "dummy":
            checkDigitAlgo = new Dummy();
            break;
        default:
            throw new RuntimeException(String.format("Invalid JSON: Unrecognized check_digit_algo: %s",
                    check_digit_algo.get("name").getAsString()));
        }
        return checkDigitAlgo;
    }
    
    static SerialNumberParser parseSerialNumberParser(JsonObject serial_number_parser) {
        if (serial_number_parser == null) {
            return new SerialNumberParsers.DefaultSerialNumberParser(null);
        }
        SerialNumberParser p;
        switch (serial_number_parser.get("name").getAsString().toLowerCase()) {
        case "default":
            JsonElement prepend = serial_number_parser.get("prepend");
            p = new SerialNumberParsers.DefaultSerialNumberParser(
                    (prepend == null) ? null : prepend.getAsString());
            break;
        default:
            throw new RuntimeException(String.format("Invalid JSON: Unrecognized serial_number_parser: %s",
                    serial_number_parser.get("name")));
        }
        return p;
    }
}
