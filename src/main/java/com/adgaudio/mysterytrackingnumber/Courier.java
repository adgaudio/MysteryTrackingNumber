package com.adgaudio.mysterytrackingnumber;

import java.util.regex.Pattern;

public abstract class Courier {
    public Courier() {}

    // NOTE: If you add a new Courier, don't forget to add the class to the TrackingNumber.couriers list.
    
    // Subclasses implement these methods:
    public abstract String getName();  // ie "Fedex Ground"
    protected abstract String getTrackingNumberRegex();
    protected abstract String getTrackingUrlFormatter();
    protected abstract Boolean hasValidCheckDigit(String trackingNumber);
    
    
    // Implemented methods below here
    
    protected final Pattern regex = Pattern.compile(getTrackingNumberRegex());
    
    protected Boolean isTrackingNumberValid(String trackingNumber) {
        if (regex.matcher(trackingNumber).matches())
            if (hasValidCheckDigit(trackingNumber)) {
                return true;
            }
        return false;
    }
    
    public String getTrackingUrl(String trackingNumber) {
        return String.format(getTrackingUrlFormatter(), trackingNumber);
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
