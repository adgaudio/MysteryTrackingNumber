package com.adgaudio.mysterytrackingnumber;

import java.util.regex.Matcher;

public class OnTrac extends Courier {

    @Override
    protected String getTrackingNumberRegex() { return "^(C[0-9]{13,13})([0-9])$"; }
    @Override
    public String getName() { return "OnTrac"; }
    @Override
    protected String getTrackingUrlFormatter() { return "http://www.ontrac.com/trackingres.asp?tracking_number=%s"; }
    @Override
    protected Boolean hasValidCheckDigit(String trackingNumber) {
      // TODO: same as UPS and others?
      Matcher m = super.regex.matcher(trackingNumber);
      m.matches(); m.groupCount();  // java bug requires both of these to be called?!
      char[] seq = m.group(1).toCharArray();
      int checkDigit = Integer.parseInt(m.group(2));
      int x = 0;
      for (int i=0 ; i<seq.length ; i++) {
        int v = Character.isDigit(seq[i]) ?
          Character.getNumericValue(seq[i]) :
          (((int) seq[i]) - 3) % 10;
        x += (v % 2 == 1) ? 2*v : v;
      }
      x = x % 10;
      if (x != 0) {
        x = 10 - x;
      }
      return x == checkDigit;
    }
}
