package com.adgaudio.mysterytrackingnumber;

import java.util.regex.Pattern;

public class Usps91 extends UspsBase {
    @Override
    protected String getTrackingNumberRegex() {
      return "^(?:420\\d{5})?(9[1-5](?:[0-9]{19}|[0-9]{23}))([0-9])$"; }
    @Override
    public String getName() { return "USPS 91"; }

    private Pattern r2 = Pattern.compile("^(420\\d{5})?9[1-5]");

    @Override
    protected Boolean hasValidCheckDigit(String trackingNumber) {
      char[] seq;
      if (!r2.matcher(trackingNumber).matches()) {
        seq = ("91" + trackingNumber.substring(0, trackingNumber.length() - 1)
            ).toCharArray();
      } else {
        seq = trackingNumber
          .substring(0, trackingNumber.length() - 1)
          .replaceAll("^420\\d{5}", "").toCharArray();
      }
      int checkDigit = Character.getNumericValue(trackingNumber.charAt(trackingNumber.length() - 1));
      return mod10Algorithm(seq, checkDigit);
    }
}

