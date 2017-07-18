package com.adgaudio.mysterytrackingnumber;

import java.util.HashMap;
import java.util.Map;

import com.adgaudio.mysterytrackingnumber.SerialNumberParsers.DefaultSerialNumberParser;
import com.google.code.regexp.Pattern;
import com.google.gson.Gson;

class CourierS10Json {
	private static Gson gson = new Gson();
	
	/* Deserialize JSON data from S10Countries */
	private class S10Country {
		public String country;
		public String country_code;
		public String courier;
		public String courier_url;
		// public String upu_reference_url;
		public String regex;
	}
	
	/* Special processing for the S10 courier */
	public static Map<String, CourierBase> fetchCouriers(CourierBase s10) {
		Map<String, CourierBase> s10Couriers = new HashMap<>();
		for (S10Country country : gson.fromJson(s10.additional.getAsJsonArray("countries"), S10Country[].class)) {
			// If courier is not specified, use the country name.
			if (country.country_code == null) {
				continue;
			}
			if (country.courier == null)
				country.courier = country.country;			
			s10Couriers.put(country.country_code, new CourierBase(
					country.courier, country.courier_url,
					Pattern.compile(country.regex),
					new CheckDigitAlgorithms.S10(),
					new DefaultSerialNumberParser(null),
					s10.additional));
		}	
		return s10Couriers;
	}
}
