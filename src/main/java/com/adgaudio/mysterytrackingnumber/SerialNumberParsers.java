package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;

public class SerialNumberParsers {

    /*
     * This interface and all implementing classes are only necessary because
     * java 7 doesn't easily support closures
     */
    public interface SerialNumberParser {
        public ArrayList<Integer> apply(String serialNumberString);
    }

    /*
     * The default way we manipulate a serial number before passing it to the
     * check digit algorithm. Treat each character as a digit. Alpha-numeric
     * characters get converted to their literal values.
     */
    static class DefaultSerialNumberParser implements SerialNumberParser {
        final String prefix;

        public DefaultSerialNumberParser(String prefix) {
            if (prefix == null)
                this.prefix = "";
            else
                this.prefix = prefix;
        }

        @Override
        public ArrayList<Integer> apply(String serialNumberString) {
            ArrayList<Integer> serialNumber = new ArrayList<>(serialNumberString.length() + 1 + prefix.length());

            for (char c : prefix.concat(serialNumberString).toCharArray()) {
                serialNumber.add(Character.isDigit(c) ? Character.getNumericValue(c) : (((int) c) - 3) % 10);
            }
            return serialNumber;
        }
    }

}
