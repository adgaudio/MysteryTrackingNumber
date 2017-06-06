package com.adgaudio.mysterytrackingnumber.couriers;

import java.util.Arrays;
import java.util.List;

import com.adgaudio.mysterytrackingnumber.Courier;
import com.adgaudio.mysterytrackingnumber.CourierTest;
import com.adgaudio.mysterytrackingnumber.couriers.FedExExpress;

public class FedExExpressTest extends CourierTest {

    @Override
    public List<String> getValidTrackingNumbers() {
        return Arrays.asList("986578788855", "477179081230", "799531274483", "790535312317", "974367662710");
    }

    @Override
    public List<String> getInvalidTrackingNumbers() {
        return Arrays.asList("996578788855");
    }

    @Override
    public Courier getCourierTestInstance() {
        return new FedExExpress();
    }

}
