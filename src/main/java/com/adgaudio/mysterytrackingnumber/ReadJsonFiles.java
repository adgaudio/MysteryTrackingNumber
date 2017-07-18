package com.adgaudio.mysterytrackingnumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class ReadJsonFiles {
	public static List<String> getResourceFiles(String path) throws IOException {
		List<String> filenames = new ArrayList<>();

		try (InputStream in = ReadJsonFiles.class.getResourceAsStream(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String resource;

			while ((resource = br.readLine()) != null) {
				filenames.add(path + "/" + resource);
			}
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
