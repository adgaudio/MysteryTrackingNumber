package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.Arrays;
import java.util.List;

import com.adgaudio.mysterytrackingnumber.Courier;
import com.adgaudio.mysterytrackingnumber.CourierTest;
import com.adgaudio.mysterytrackingnumber.couriers.Ups;


public class UpsTest extends CourierTest {

    @Override
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList(
            "1Z5R89390357567127", "1Z879E930346834440", "1Z410E7W0392751591", "1Z8V92A70367203024");
    }

    @Override
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList("2Z5R89390357567127",
            "1A5R89390357567127", "1Z1111111111111111");
    }

    @Override
    public Courier getCourierTestInstance() {
        return new Ups();
    }

}
