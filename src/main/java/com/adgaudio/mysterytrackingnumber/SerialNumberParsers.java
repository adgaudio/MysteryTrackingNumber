package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;
import com.google.code.regexp.Pattern;

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
     * 
     * If the serial number matches the given regex, prepend the given prefix.
     */
    static class DefaultSerialNumberParser implements SerialNumberParser {
        final String prefix;
        final Pattern regex;

        public DefaultSerialNumberParser(Pattern regex, String prefix) {
            this.prefix = prefix;
            this.regex = regex;
        }
        
        public DefaultSerialNumberParser() {
        	this(null, null);
        }

		@Override
        public ArrayList<Integer> apply(String serialNumberString) {
    		ArrayList<Integer> serialNumber = new ArrayList<>(
    				serialNumberString.length() + 1 + (this.regex == null ? 0 : prefix.length()));
    		String arr;
    		if ((this.regex != null) && (this.regex.matcher(serialNumberString).matches())) 
    			arr = prefix.concat(serialNumberString);
    		else
    			arr = serialNumberString;
            for (char c : arr.toCharArray()) {
                serialNumber.add(Character.isDigit(c) ? Character.getNumericValue(c) : (((int) c) - 3) % 10);
            }
            return serialNumber;
        }
    }

}
