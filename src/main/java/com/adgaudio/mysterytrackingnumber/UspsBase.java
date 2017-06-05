package com.adgaudio.mysterytrackingnumber;

public abstract class UspsBase extends Courier {

  @Override
  protected String getTrackingUrlFormatter() {
    return "https://tools.usps.com/go/TrackConfirmAction?tLabels=%s"; }

  protected boolean mod10Algorithm(char[] seq, int checkDigit) {
      int x = 0;
      int v;
      for (int i=seq.length-1 ; i>=0 ; i--) {
        v = Character.getNumericValue(seq[i]);
        x += (v % 2 == 0) ? 3 * v : v;
      }
      x = x % 10;
      if (x != 0) {
        x = 10 - x;
      }
      return x == checkDigit;
    }
}
