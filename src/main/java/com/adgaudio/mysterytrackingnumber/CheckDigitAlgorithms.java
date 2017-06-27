package com.adgaudio.mysterytrackingnumber;

import java.util.ArrayList;

public final class CheckDigitAlgorithms {
    private static boolean mod7(ArrayList<Integer> arr, int checkDigit) {
        StringBuilder strNum = new StringBuilder();
        for (int num : arr) 
             strNum.append(num);
        Long x = Long.parseLong(strNum.toString());
        return x % 7 == checkDigit;
    }
    
    private static boolean mod10(ArrayList<Integer> arr, int checkDigit, int evensMultiplier, int oddsMultiplier) {        
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
    
    private static boolean s10(ArrayList<Integer> seq1, int checkDigit) {
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
    
    private static boolean sumProductWithWeightingsAndModulo(ArrayList<Integer> arr, int checkDigit, int[] weightings, int modulo1, int modulo2) {
        int x = 0;
        int i = 0;
        for (int v : arr) {
            x += v * weightings[i];
            i += 1;
        }
        return x % modulo1 % modulo2 == checkDigit;
    }
    
    
    /* This interface and all implementing classes are
     * only necessary because java 7 doesn't easily support closures */
    public interface CheckDigitAlgo {
        public boolean apply(ArrayList<Integer> arr, int checkDigit);
    }
    
    static class Mod10 implements CheckDigitAlgo {
        public final int evensMultiplier;
        public final int oddsMultiplier; 
        public Mod10(int evensMultiplier, int oddsMultiplier) {
            this.evensMultiplier = evensMultiplier;
            this.oddsMultiplier = oddsMultiplier;
        }   
        public boolean apply(ArrayList<Integer> arr, int checkDigit) {
            return mod10(arr, checkDigit, evensMultiplier, oddsMultiplier);
        }
    }
    
    static class Mod7 implements CheckDigitAlgo {
        public boolean apply(ArrayList<Integer> arr, int checkDigit) {
            return mod7(arr, checkDigit);
        }
    }
    static class S10 implements CheckDigitAlgo {
        public boolean apply(ArrayList<Integer> arr, int checkDigit) {
            return s10(arr, checkDigit);
        }
    }
    
    static class SumProductWithWeightingsAndModulo implements CheckDigitAlgo {
        public final int[] weightings;
        public final int modulo1;
        public final int modulo2;
        public SumProductWithWeightingsAndModulo(
                int[] weightings, int modulo1, int modulo2) {
            this.weightings = weightings;
            this.modulo1 = modulo1;
            this.modulo2 = modulo2;      
        }
        public boolean apply(ArrayList<Integer> arr, int checkDigit) {
            return sumProductWithWeightingsAndModulo(arr, checkDigit, weightings, modulo1, modulo2);
        }
    }
}
