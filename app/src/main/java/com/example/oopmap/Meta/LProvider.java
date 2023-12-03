package com.example.oopmap.Meta;

public class LProvider {
    static public int x, y;
    static public boolean paused = false;
    static public int MAX_SIZE = 100;
    static public boolean real = false;
    static public String text = "";

    static float getProjection(int orig) {
        if (real) {
            float res = orig;
            res += 300;
            return res;
        }
        float res = (float) (orig * 20 / Math.sqrt(orig * orig + 1600));
        res += 20;
        res *= 15;
        return res;
    }
}
