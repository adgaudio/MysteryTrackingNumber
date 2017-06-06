package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Courier {
    public Courier() {
    }

    // NOTE: If you add a new Courier, don't forget to add the class to the
    // TrackingNumber.couriers list.

    // Subclasses implement these methods:
    public abstract String getName(); // ie "Fedex Ground"

    protected abstract String getTrackingNumberRegex();

    protected abstract String getTrackingUrlFormatter();

    protected abstract Boolean hasValidCheckDigit(ArrayList<Integer> arr, int checkDigit);

    // Implemented methods below here

    protected final Pattern regex = Pattern.compile(getTrackingNumberRegex());

    protected Boolean isTrackingNumberValid(String trackingNumber) {
        Matcher m = regex.matcher(trackingNumber);

        if (m.matches()) {
            if (m.groupCount() != 2) {
                throw new RuntimeException(
                        "Code Error: the regex for your courier must include at least two capturing groups");
            }
            ArrayList<Integer> arr = parseCheckDigitSequence(m.group(1));
            int checkDigit = Integer.parseInt(m.group(2));

            return hasValidCheckDigit(arr, checkDigit);
        }
        return false;
    }

    /*
     * The default helper method for Couriers used as a pre-processing step to
     * evaluate hasValidCheckDigit. This method assumes the check digit sequence
     * is a string of digits, and just converts each digit into its literal
     * numeric value.
     * 
     * Sub-classes can override this method if necessary. For instance, if the
     * checkDigit algorithm needs to do something with alphanumeric characters
     * and not just digits, it should override this method to pre-process the
     * captured substring.
     */
    protected ArrayList<Integer> parseCheckDigitSequence(String capturedRegexGroup) {
        ArrayList<Integer> arr = new ArrayList<>(capturedRegexGroup.length() + 1);
        for (char c : capturedRegexGroup.toCharArray()) {
            arr.add(Character.isDigit(c) ? Character.getNumericValue(c) : (((int) c) - 3) % 10);
        }
        return arr;
    }

    public String getTrackingUrl(String trackingNumber) {
        return String.format(getTrackingUrlFormatter(), trackingNumber);
    }

    @Override
    public String toString() {
        return getName();
    }
}
