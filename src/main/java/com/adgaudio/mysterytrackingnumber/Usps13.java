package com.adgaudio.mysterytrackingnumber;

public class Usps13 extends UspsBase {
    @Override
    protected String getTrackingNumberRegex() {
      return "^([A-Z]{2,2})([0-9]{9,9})([A-Z]{2,2})$"; }
    @Override
    public String getName() { return "USPS13"; }
    @Override
    protected Boolean hasValidCheckDigit(String trackingNumber) {
      String tmp = trackingNumber.replaceAll("\\D+","");
      int checkDigit = Character.getNumericValue(tmp.charAt(tmp.length()-1));
      char[] seq1 = tmp.substring(0, tmp.length()-1).toCharArray();
      int[] seq2 = {8,6,4,2,3,5,9,7};
      int x = 0;
      for (int i=0; i<8; i++) {
        x += Character.getNumericValue(seq1[i]) * seq2[i];
      }
      int r = x % 11;
      if (r == 1) {
        r = 0;
      } else if (r == 0) {
        r = 5;
      } else {
        r = 11 - r;
      }
      return r == checkDigit;
    }
}
