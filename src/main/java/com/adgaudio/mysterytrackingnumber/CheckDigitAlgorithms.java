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
    
    public static boolean sumOfProductsWithWeightingsMod11(ArrayList<Integer> seq1, int checkDigit) {
        int[] weightings = {8,6,4,2,3,5,9,7};
        int x = 0;
        int i = 0;
        for (int v : seq1) {
            x += v * weightings[i];
            i += 1;
        }
        int r = x % 11;
        if (r == 1
                ) {
            r = 0;
        } else if (r == 0) {
            r = 5;
        } else {
            r = 11 - r;
        }
        return r == checkDigit;
    }
}
