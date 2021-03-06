package com.adgaudio.mysterytrackingnumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class ReadJsonFiles {
	public static List<String> getJsonFilepaths() throws IOException {
		 List<String> filenames = new ArrayList<>();
		 BufferedReader br = openFile("/couriers.txt");
		 for (String line = br.readLine(); line != null; line = br.readLine()) {
			 filenames.add(line.trim());
		 }
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
