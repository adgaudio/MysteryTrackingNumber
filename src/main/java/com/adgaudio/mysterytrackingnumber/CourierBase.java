package com.adgaudio.mysterytrackingnumber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.CheckDigitAlgo;
import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.Dummy;
import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.Mod10;
import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.Mod7;
import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.S10;
import com.adgaudio.mysterytrackingnumber.CheckDigitAlgorithms.SumProductWithWeightingsAndModulo;
import com.adgaudio.mysterytrackingnumber.SerialNumberParsers.SerialNumberParser;
import com.google.code.regexp.Pattern;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class CourierBase {
	public final String name;
	public final String parentName;
	public final String trackingUrl;
	protected final Pattern regex;
	protected final CheckDigitAlgo checkDigitAlgo;
	protected final SerialNumberParser serialNumberParser;
	protected final AdditionalValidation additionalValidation;
	protected final AdditionalData additional;  // used by subclasses
	
	@Override
	public String toString() {
		return name;
	}

	static Gson gson = new Gson();

	public CourierBase(String name, String parentName, String trackingUrl, Pattern regex, CheckDigitAlgo checkDigitAlgo,
			SerialNumberParser serialNumberParser, AdditionalValidation additionalValidation,
			AdditionalData additional) {
		this.name = name;
		this.parentName = parentName;
		this.trackingUrl = trackingUrl;
		this.regex = regex;
		this.checkDigitAlgo = checkDigitAlgo;
		this.serialNumberParser = serialNumberParser;
		this.additionalValidation = additionalValidation;
		this.additional = additional;
	}

	/* Return list of couriers we recognize */
	protected static List<CourierBase> fetchCouriers() {
		ArrayList<CourierBase> couriers = new ArrayList<>();
		for (CourierJsonData courier : getCourierJsonData()) {
			couriers.add(new CourierBase(
					courier.name,
					courier.parentName,
					courier.tracking_url,
					parseRegex(courier.regex),
					parseCheckDigitAlgo(courier.validation.checksum),
					parseSerialNumberFormat(courier.validation.serial_number_format),
					courier.validation.additional,
					parseAdditional(courier.additional)));
		}
		return couriers;
	}
	
	/* Load JSON files from disk */
	protected static List<CourierJsonData> getCourierJsonData() {
		ArrayList<CourierJsonData> couriersJsonData = new ArrayList<>();
		try {
			for (String fp : ReadJsonFiles.getJsonFilepaths()) {
				JsonObject obj = new JsonParser()
						.parse(ReadJsonFiles.openFile(fp))
						.getAsJsonObject();
				for (CourierJsonData courier : new Gson().fromJson(obj.getAsJsonArray("tracking_numbers"), CourierJsonData[].class)) {
					courier.parentName = obj.get("name").getAsString();
					if (courier.parentName == null)
						throw new RuntimeException("Bug in json: Each json file must define a \"name\" key at top level.");
					couriersJsonData.add(courier);
				}
			}
		} catch (JsonSyntaxException | IOException e) {
			// this would be a bug caused by invalid JSON
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return couriersJsonData;
	}

	/* Internal class  used to identify additional validation fields required to parse tracking number.
	 * For instance, S10 uses this to evaluate country */
    protected class AdditionalValidation {
    	public ArrayList<String> exists;
    }
    
	/* Internal class used for parsing JSON files */
	class CourierJsonData {
		class CourierJsonValidation {
		    public AdditionalValidation additional;
		    public JsonObject checksum;
		    public SerialNumberFormat serial_number_format;
		}
		
		public String name;
		public String tracking_url;
		public JsonElement regex;
		public CourierJsonValidation validation;
		public JsonArray additional;
		
		public String parentName;
	}
	/* Internal class for parsing JSON files */
	class SerialNumberFormat {
		public PrependIf prepend_if;

		class PrependIf {
			public String matches_regex;
			public String content;
		}
	}
	
	/* Standard way regular expressions are parsed in our JSON data */
	static Pattern parseRegex(JsonElement regex) {
		String tmpRegex;
		if (regex.isJsonArray()) {
			StringBuilder builder = new StringBuilder();
			for(String s : gson.fromJson(regex, String[].class)) {
			    builder.append(s);
			}
			tmpRegex = builder.toString();
		} else {
			tmpRegex = regex.getAsString();
		}
		return Pattern.compile(tmpRegex);
	}

	/*
	 * Map check digit parameters defined in JSON data to check digit algorithms.
	 */
	static CheckDigitAlgo parseCheckDigitAlgo(JsonObject checksum) {
		CheckDigitAlgo checkDigitAlgo = null;
		if (checksum.size() == 0) {
			return new Dummy();
		}
		switch (checksum.get("name").getAsString().toLowerCase()) {
		case "mod10":
			checkDigitAlgo = new Mod10(checksum.get("evens_multiplier").getAsInt(),
					checksum.get("odds_multiplier").getAsInt());
			break;
		case "mod7":
			checkDigitAlgo = new Mod7();
			break;
		case "s10":
			checkDigitAlgo = new S10();
			break;
		case "sum_product_with_weightings_and_modulo":
			checkDigitAlgo = new SumProductWithWeightingsAndModulo(
					gson.fromJson(checksum.get("weightings").getAsJsonArray(), int[].class),
					checksum.get("modulo1").getAsInt(), checksum.get("modulo2").getAsInt());
			break;
		case "dummy":
			checkDigitAlgo = new Dummy();
			break;
		default:
			throw new RuntimeException(String.format("Invalid JSON: Unrecognized check_digit_algo: %s",
					checksum.get("name").getAsString()));
		}
		return checkDigitAlgo;
	}

	static SerialNumberParser parseSerialNumberFormat(SerialNumberFormat serial_number_format) {
		SerialNumberParser p;
		if (serial_number_format == null) {
			return new SerialNumberParsers.DefaultSerialNumberParser();
		} else if (serial_number_format.prepend_if != null) {
			p = new SerialNumberParsers.DefaultSerialNumberParser(
					Pattern.compile(serial_number_format.prepend_if.matches_regex),
					serial_number_format.prepend_if.content
					);
		} else {
			p = new SerialNumberParsers.DefaultSerialNumberParser();
		}
		return p;
	}
	
	private static class AdditionalDataJson {
		public String name;
		public String regex_group_name;
		public List<HashMap<String, String>> lookup;
	}
	public static class AdditionalData extends HashMap<String, AdditionalDatum> {
		private static final long serialVersionUID = 3077770363661360724L;
	}
	
	public static class AdditionalDatum {
		public final String name;
		public final String regexGroupName;
		public final Map<String, Map<String, String>> lookup;
		
		public AdditionalDatum(String name, String regexGroupName, Map<String, Map<String, String>> lookup) {
			this.name = name;
			this.regexGroupName = regexGroupName;
			this.lookup = lookup;
		}
	}
	
	static AdditionalData parseAdditional(JsonArray additional) {
		AdditionalData map = new AdditionalData();
		if ((additional == null) || (additional.isJsonNull()))
			return map;
		
		AdditionalDataJson[] tmp = gson.fromJson(additional, AdditionalDataJson[].class);
		for (int i=0 ; i<tmp.length ; i++) {
			Map<String, Map<String, String>> lookup = new HashMap<>();
			for (HashMap<String, String> l : tmp[i].lookup) {
				lookup.put(l.get("matches"), l);
			}
			map.put(tmp[i].name, new AdditionalDatum(tmp[i].name, tmp[i].regex_group_name, lookup));
		}
		return map;
	}
}
