package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrackingNumber {
	final CourierBase courier;
	public final String trackingNumber;

	public TrackingNumber(String trackingNumber, CourierBase courier) {
		this.trackingNumber = trackingNumber;
		this.courier = courier;
	}

	public static TrackingNumber parse(String trackingNumber) {
		TrackingNumberParser p = new TrackingNumberParser(trackingNumber);
		return new TrackingNumber(p.trackingNumber, p.courier);
	}

	public static List<TrackingNumber> filterAndParseTrackingNumbers(Collection<String> barcodes) {
		List<TrackingNumber> res = new ArrayList<>();
		for (String s : barcodes) {
			TrackingNumber t = TrackingNumber.parse(s);
			if (new UnrecognizedCourier().getClass() != t.courier.getClass()) {
				res.add(t);
			}
		}
		return res;
	}

	public Boolean isCourierRecognized() {
		return !(courier instanceof UnrecognizedCourier);
	}

	public String getCourierName() {
		return courier.name;
	}

	public String getTrackingUrl() {
		if (courier.trackingUrl != null) {
			return String.format(courier.trackingUrl, trackingNumber);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return String.format("%s - %s", this.courier, this.trackingNumber);
	}

	@Override
	public int hashCode() {
		return this.trackingNumber.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TrackingNumber))
			return false;
		if (obj == this)
			return true;
		return this.trackingNumber.equals(((TrackingNumber) obj).trackingNumber);
	}
}
