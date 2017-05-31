package com.adgaudio.mysterytrackingnumber;

import java.util.regex.Pattern;

public abstract class Courier {
    
    // Subclasses implement these methods:
    protected abstract Pattern getTrackingNumberRegex();
    protected abstract String getTrackingUrlFormatter();
    public abstract String getName();  // ie "Fedex Ground"
    protected abstract Boolean hasValidCheckDigit(String trackingNumber);
    
    
    // Implemented methods below here
    
    protected final Pattern regex = getTrackingNumberRegex();
    
    public Boolean isTrackingNumberValid(String trackingNumber) {
        return regex.matcher(trackingNumber).matches() && hasValidCheckDigit(trackingNumber);
    }
    
    public String getTrackingUrl(String trackingNumber) {
        return String.format(getTrackingUrlFormatter(), trackingNumber);
    }
    
    @Override
    public String toString() {
        return getName();
    }

}
