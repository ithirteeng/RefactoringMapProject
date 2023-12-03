package com.example.oopmap.Meta;

import java.util.ArrayList;
import java.util.List;

public class PheromoneMap {
    List<List<List<Double>>> pheromons;
    private int its = 60;

    PheromoneMap() {
        int i = 0;
        pheromons = new ArrayList<>();
        while (i < LProvider.MAX_SIZE) {
            pheromons.add(new ArrayList<List<Double>>());
            int j = 0;
            while (j < LProvider.MAX_SIZE) {
                pheromons.get(i).add(new ArrayList<Double>());
                int k = 0;
                while (k < 7) {
                    pheromons.get(i).get(j).add(0.0);
                    k++;
                }
                j++;
            }
            i++;
        }

    }

    public void update() {
        if (its == 0) {
            int i = 0;
            while (i < LProvider.MAX_SIZE - 1) {
                int j = 0;
                while (j < LProvider.MAX_SIZE - 1) {
                    approx(i, j, i + 1, j);
                    approx(i, j, i, j + 1);
                    j++;
                }
                i++;
            }
            its = 10;
        } else {
            its--;
        }
    }

    public Double getPheromones(int x, int y, int type) {
        x = (x + LProvider.MAX_SIZE) % LProvider.MAX_SIZE;
        y = (y + LProvider.MAX_SIZE) % LProvider.MAX_SIZE;
        return pheromons.get(x).get(y).get(type);
    }

    public void increaseBy(int x, int y, int type, double value) {

        x = (x + LProvider.MAX_SIZE) % LProvider.MAX_SIZE;
        y = (y + LProvider.MAX_SIZE) % LProvider.MAX_SIZE;
        pheromons.get(x).get(y).set(type, pheromons.get(x).get(y).get(type) + value);
    }

    private void approx(int x1, int y1, int x2, int y2) {
        int i = 0;
        while (i < 7) {
            double sum = pheromons.get(x1).get(y1).get(i) + pheromons.get(x2).get(y2).get(i);
            pheromons.get(x1).get(y1).set(i, sum / 2);
            pheromons.get(x2).get(y2).set(i, sum / 2);
            i++;
        }
    }
}
