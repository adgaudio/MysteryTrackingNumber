package com.adgaudio.mysterytrackingnumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class TrackingNumberTest {

	List<String> validTrackingNumbers = Arrays.asList("73891051146", "3318810025", "986578788855");
	List<String> courierNames = Arrays.asList("DHL Express Air", "DHL Express", "FedEx Express (13)");

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

	@Test
	public void testJsonFixtures() {

		JsonArray fixtures1 = new JsonParser()
				.parse(CourierBase.openFile("/couriers.json"))
				.getAsJsonObject()
				.get("couriers").getAsJsonArray();
		Fixture1[] fixtures = new Gson().fromJson(fixtures1, Fixture1[].class);


		for (Fixture1 testData : fixtures) {
			String courierName = testData.name;
			for (String trackingNumber : testData.test_numbers.valid) {
				TrackingNumberParser tnp = new TrackingNumberParser(trackingNumber);
				assertTrue(courierName + " regex should be valid: " + trackingNumber, tnp.match.matches());
				if (courierName.equals("S10")) {
					assertTrue(courierName + " check digit should be valid: " + trackingNumber,
							tnp.courier instanceof CourierS10Json);
					assertEquals("Tracking number should be recognized: " + trackingNumber, tnp.courier.name,
							TrackingNumber.parse(trackingNumber).courier.name);
				} else {
					System.out.println(courierName + " " + tnp.courier.name);
					System.out.println(trackingNumber);
					System.out.println(tnp.match.matches());
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
