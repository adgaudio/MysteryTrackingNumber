package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;

public final class CheckDigitAlgorithms {

    public static boolean mod7(ArrayList<Integer> arr, int checkDigit) {
        StringBuilder strNum = new StringBuilder();
        for (int num : arr) 
             strNum.append(num);
        Long x = Long.parseLong(strNum.toString());
        return x % 7 == checkDigit;
    }
    
    public static boolean mod10(ArrayList<Integer> arr, int checkDigit, int evensMultiplier, int oddsMultiplier) {
        int x = 0;
        int i = 0;
        for (Integer v : arr) {
            x += (i % 2 == 0) ? evensMultiplier * v : oddsMultiplier * v;
            i += 1;
        }
        x = x % 10;
        if (x != 0) {
            x = 10 - x;
        }
        return x == checkDigit;
    }
    
    public static boolean s10_check_digit_algo(ArrayList<Integer> seq1, int checkDigit) {
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
    
    public static boolean sumProductWithWeightingsAndModulo(ArrayList<Integer> seq1, int checkDigit, int[] weightings, int modulo1, int modulo2) {
        int x = 0;
        int i = 0;
        for (int v : seq1) {
            x += v * weightings[i];
            i += 1;
        }
        return x % modulo1 % modulo2 == checkDigit;
    }
}
