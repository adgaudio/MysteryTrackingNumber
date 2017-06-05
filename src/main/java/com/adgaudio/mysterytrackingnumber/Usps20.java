package com.adgaudio.mysterytrackingnumber;

public class Usps20 extends UspsBase {
    @Override
    protected String getTrackingNumberRegex() {
      return "^([0-9]{2,2})([0-9]{9,9})([0-9]{8,8})([0-9])$"; }
    @Override
    public String getName() { return "USPS20"; }
    @Override
    protected Boolean hasValidCheckDigit(String trackingNumber) {
      char[] seq = trackingNumber.substring(0, trackingNumber.length() - 1).toCharArray();
      int checkDigit = trackingNumber.charAt(trackingNumber.length() - 1);
      return mod10Algorithm(seq, checkDigit);
    }
}
