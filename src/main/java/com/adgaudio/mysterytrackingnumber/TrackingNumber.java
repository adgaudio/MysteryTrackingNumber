package com.adgaudio.mysterytrackingnumber;

import java.util.List;

public class TrackingNumber {
	final CourierBase courier;
	public final String trackingNumber;

	public TrackingNumber(String trackingNumber, CourierBase courier) {
		this.trackingNumber = trackingNumber;
		this.courier = courier;
	}

	public static TrackingNumber parse(String trackingNumber) {
		List<CourierBase> couriers = TrackingNumberParser.parse(trackingNumber);
		if (couriers.isEmpty())
			return new TrackingNumber(trackingNumber, new UnrecognizedCourier());
		else if (couriers.size() == 1)
			return new TrackingNumber(trackingNumber, couriers.get(0));
		else
//			throw new RuntimeException("BUG: Multiple couriers detected for tracking number: " + trackingNumber);
			return new TrackingNumber(trackingNumber, new MultipleCouriers(couriers));
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
