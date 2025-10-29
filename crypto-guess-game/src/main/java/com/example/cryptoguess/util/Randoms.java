package com.example.cryptoguess.util;


import java.util.List;
import java.util.Random;

public class Randoms {
    private static final Random R = new Random();
    public static int nextIndex(int bound) { return R.nextInt(bound); }
    public static int[] twoDistinct(int bound) {
        int a = nextIndex(bound);
        int b;
        do { b = nextIndex(bound); } while (b == a);
        return new int[]{a, b};
    }
}
