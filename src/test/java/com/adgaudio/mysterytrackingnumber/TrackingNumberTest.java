package com.adgaudio.mysterytrackingnumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class TrackingNumberTest {

	List<String> validTrackingNumbers = Arrays.asList("73891051146", "3318810025", "986578788855");
	List<String> courierNames = Arrays.asList("DHL Express Air", "DHL Express", "FedEx Express (13)");

	List<String> validS10Numbers = Arrays.asList("RB123456785US", "RB123456785GB", "RB123456785CN");
	List<String> s10CourierNames = Arrays.asList(
			"United States Postal Service", "Royal Mail Group plc", "China Post");
	
	@Test
	public void testParseInvalid() {
		assertTrue(TrackingNumber.parse("invalid tracking number").courier instanceof UnrecognizedCourier);
		assertTrue(TrackingNumber.parse("").courier instanceof UnrecognizedCourier);
	}

	@Test
	public void testIsCourierRecognized() {
		assertFalse(TrackingNumber.parse("").isCourierRecognized());
		assertTrue(TrackingNumber.parse(validTrackingNumbers.get(0)).isCourierRecognized());
	}

	@Test
	public void testParseValid() {
		for (int i = 0; i < validTrackingNumbers.size(); i++) {
			TrackingNumber tn = TrackingNumber.parse(validTrackingNumbers.get(i));
			assertNotNull("Should be a recognized tracking number: " + validTrackingNumbers.get(i), tn);
			assertEquals(tn.getCourierName(), courierNames.get(i));
			assertEquals(tn.getCourierName(), tn.courier.name);
		}
	}
	
	@Test
	public void testParseValidS10() {
		for (int i = 0; i < validS10Numbers.size(); i++) {
			TrackingNumber tn = TrackingNumber.parse(validS10Numbers.get(i));
			assertNotNull("Should be a recognized tracking number: " + validS10Numbers.get(i), tn);
			assertEquals(s10CourierNames.get(i), tn.getCourierName());
			assertEquals(tn.getCourierName(), tn.courier.name);
		}
	}

	@Test
	public void testFilterAndParseTrackingNumbers() {
		ArrayList<String> arr = new ArrayList<>();
		arr.add("not trackingnumber");
		arr.addAll(validTrackingNumbers);
		arr.add(validTrackingNumbers.get(0) + " ");
		arr.add(" " + validTrackingNumbers.get(0));

		List<TrackingNumber> res = TrackingNumber.filterAndParseTrackingNumbers(arr);
		assertEquals(res.size(), validTrackingNumbers.size());
		for (TrackingNumber tn : res) {
			assertNotNull("filterAndParseTrackingNumbers should never return a null value", tn);
		}
	}

	@Test
	public void testEquals() {
		TrackingNumber tn0 = TrackingNumber.parse(validTrackingNumbers.get(0));
		TrackingNumber tn1 = TrackingNumber.parse(validTrackingNumbers.get(1));
		assertEquals(tn0, tn0);
		assertNotEquals(tn0, tn1);
	}

	@Test
	public void testHashCode() {
		TrackingNumber tn0 = TrackingNumber.parse(validTrackingNumbers.get(0));
		assertEquals(tn0.hashCode(), validTrackingNumbers.get(0).hashCode());
	}

	public class Fixture2 {
		String[] valid;
		String[] invalid;
	}
	public class Fixture1 {
		Fixture2 test_numbers;
		String name;
	}
	
	protected static List<Fixture1> getFixtures() {
		ArrayList<Fixture1> fixtures = new ArrayList<>();
		try {
			for (String fp : ReadJsonFiles.getJsonFilepaths()) {
				
				for (Fixture1 courier : new Gson().fromJson(new JsonParser().parse(ReadJsonFiles.openFile(fp)), Fixture1[].class)) {
					fixtures.add(courier);
				}
			}
		} catch (JsonSyntaxException | IOException e) {
			// this would be a bug caused by invalid JSON
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return fixtures;
	}

	@Test
	public void testJsonFixtures() {
		for (Fixture1 testData : getFixtures()) {
			String courierName = testData.name;
			for (String trackingNumber : testData.test_numbers.valid) {
				TrackingNumberParser tnp = new TrackingNumberParser(trackingNumber);
				assertTrue(courierName + " regex should be valid: " + trackingNumber, tnp.match.matches());
				if (courierName.equals("S10")) {
					assertTrue(courierName + " check digit should be valid: " + trackingNumber,
							tnp.courier instanceof CourierBase);
					assertEquals("Tracking number should be recognized: " + trackingNumber, tnp.courier.name,
							TrackingNumber.parse(trackingNumber).courier.name);
				} else {
					assertEquals(courierName + " check digit should be valid: " + trackingNumber, courierName,
							tnp.courier.name);
					assertEquals("Tracking number should be recognized: " + trackingNumber, courierName,
							TrackingNumber.parse(trackingNumber).getCourierName());
				}
			}
			for (String trackingNumber : testData.test_numbers.invalid) {
				assertNotEquals("Tracking number should not be recognized: " + trackingNumber, courierName,
						TrackingNumber.parse(trackingNumber).courier.name);
			}
		}
	}
}
