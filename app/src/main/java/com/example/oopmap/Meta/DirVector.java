package com.example.oopmap.Meta;

public class DirVector {
    public int x;
    public int y;

    public DirVector(int dir) {
        dir = (dir + 8) % 8;
        switch (dir % 8) {
            case 1: {
                x = -1;
                y = 1;
                break;
            }
            case 2: {
                x = 0;
                y = 1;
                break;
            }
            case 3: {
                x = 1;
                y = 1;
                break;
            }
            case 4: {
                x = 1;
                y = 0;
                break;
            }
            case 5: {
                x = 1;
                y = -1;
                break;
            }
            case 6: {
                x = 0;
                y = -1;
                break;
            }
            case 7: {
                x = -1;
                y = -1;
                break;
            }
            default: {
                x = -1;
                y = 0;
            }
        }

    }
}
