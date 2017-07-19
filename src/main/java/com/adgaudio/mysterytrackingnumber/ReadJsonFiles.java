package com.adgaudio.mysterytrackingnumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ReadJsonFiles {
	public static List<String> getResourceFiles(String path) throws IOException {
		// TODO Hack: cannot for the life of me figure out how to 
		// read the list of json files in Eclipse and from jar in Android.
		// So I hardcoded it.
		List<String> filenames = new ArrayList<>();
		 filenames.addAll(Arrays.asList(
		 "/couriers/usps.json", "/couriers/s10.json", "/couriers/dhl.json",
		 "/couriers/misc.json", "/couriers/fedex.json"));
		 return filenames;
	}

	static BufferedReader openFile(String filepath) {
		InputStream in = ReadJsonFiles.class.getResourceAsStream(filepath);
		if (in == null) {
			throw new RuntimeException("Could not load resource: " + filepath);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		return br;
	}
}
