package com.example.oopmap.Meta;

import java.util.Random;

public class TrueRand {
    public static Random random = new Random();

    public static Boolean nextBoolean() {
        return random.nextBoolean();
    }
}
