package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;

public final class CheckDigitAlgorithms {

    public static boolean mod10(ArrayList<Integer> arr, int checkDigit, int multiplier, boolean multiplyIfEven) {
        int x = 0;
        int evenOrOdd;
        if (multiplyIfEven) {
            evenOrOdd = 0;
        } else {
            evenOrOdd = 1;
        }
        int i = 0;
        for (Integer v : arr) {
            x += (i % 2 != evenOrOdd) ? multiplier * v : v;
            i += 1;
        }
        x = x % 10;
        if (x != 0) {
            x = 10 - x;
        }
        return x == checkDigit;
    }
}
