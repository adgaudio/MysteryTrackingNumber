package com.adgaudio.mysterytrackingnumber;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.CheckDigitAlgo;
import com.adgaudio.mysterytrackingnumber.SerialNumberParsers.DefaultSerialNumberParser;
import com.adgaudio.mysterytrackingnumber.SerialNumberParsers.SerialNumberParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class CourierS10Json extends CourierBase {
	private static Gson gson = new Gson();

	/* Deserialize JSON data from S10Countries */
	private class S10Countries {
		public String country;
		public String country_code;
		public String courier;
		public String courier_url;
		// public String upu_reference_url;
		public JsonElement regex;
	}

	public CourierS10Json(String name, String trackingUrl, Pattern regex, CheckDigitAlgo checkDigitAlgo,
			SerialNumberParser serialNumberParser) {
		super(name, trackingUrl, regex, checkDigitAlgo, serialNumberParser);
	}

	protected static Map<String, CourierS10Json> fetchCouriers() {
		// Fetch and parse json file
		String filepathS10Countries = "/s10_countries.json";
		String filepathS10Overrides = "/s10_overrides.json";

		JsonObject couriersObj = new JsonParser().parse(openFile(filepathS10Countries)).getAsJsonObject();

		S10Countries[] countries = gson.fromJson(couriersObj.getAsJsonArray("s10_countries"), S10Countries[].class);
		Map<String, CourierJsonData> overrides = gson.fromJson(openFile(filepathS10Overrides),
				new TypeToken<Map<String, CourierJsonData>>() {
				}.getType());

		Map<String, CourierS10Json> couriers = new HashMap<>(countries.length);
		for (S10Countries country : countries) {
			if (overrides.containsKey(country.country)) {
				CourierJsonData newsettings = overrides.get(country.country);
				if (newsettings.name != null)
					country.courier = newsettings.name;
				if (newsettings.tracking_url != null)
					country.courier_url = newsettings.tracking_url;
			}
			// If courier is not specified, use the country name.
			if (country.courier == null)
				country.courier = country.country;
			if (!country.regex.isJsonNull()) {
				CourierS10Json tmp = new CourierS10Json(country.courier, country.courier_url, parseRegex(country.regex),
						new CheckDigitAlgorithms.S10(), new DefaultSerialNumberParser(null));
				couriers.put(country.country_code, tmp);
			}
		}
		return couriers;
	}
}
