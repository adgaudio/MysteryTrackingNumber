package com.adgaudio.mysterytrackingnumber;

import java.util.List;

public class MultipleCouriers extends CourierBase {
	public final List<CourierBase> couriers;
	
	public MultipleCouriers(List<CourierBase> couriers) {
		super("Multiple Couriers", "Multiple Couriers", null, null, null, null, null, null);
		this.couriers = couriers;
	}
}
