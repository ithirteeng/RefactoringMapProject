package com.example.oopmap.Crits;

import com.example.oopmap.Meta.CreatureMap;
import com.example.oopmap.Meta.DirVector;
import com.example.oopmap.Tiles.GrassTile;
import com.example.oopmap.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Brain implements Cloneable {
    protected List<Double> weights = new ArrayList<>();
    protected List<Double> weights2 = new ArrayList<>();
    protected List<List<Double>> c_weights = new ArrayList<>();
    protected Class resClass;
    protected int type;
    protected int wl = 8;

    public Brain(int type) {
        this(type, GrassTile.class);
    }

    public Brain(int type, Class resClass) {
        zeroSetup();
        this.resClass = resClass;
        this.type = type;
        initNeurons();
    }

    protected void zeroSetup() {

    }

    protected void initNeurons() {
        Random r = new Random();
        int i = 0;
        c_weights.add(new ArrayList<Double>());
        c_weights.add(new ArrayList<Double>());
        while (i < wl) {
            weights.add(r.nextDouble() * 2 - 1);
            weights2.add(r.nextDouble() * 2 - 1);
            int h = 0;
            while (h < c_weights.size()) {
                c_weights.get(h).add(r.nextDouble() * 2 - 1);
                h++;
            }
            i++;
        }
    }

    public double getResult(Creature owner) {
        double res = 0;
        int i = 0;
        List<Double> preresults = new ArrayList<>();
        while (i < 5) {
            int j = 0;
            DirVector dirs = new DirVector(owner.getDirection() + i);
            DirVector dirsCo = new DirVector(owner.getDirection() + i + 2);
            preresults.add(0.0);
            while (j < 5) {
                int k = -1;
                while (k < 2) {
                    Tile cur_r = owner.getCurrentTile();
                    Tile cur = cur_r.homeMap.getTile(cur_r.x + dirsCo.x * k, cur_r.y + dirsCo.y * k);
                    if (type == 0) {
                        Tile chTile = cur.homeMap.getTile(cur.x + dirs.x * j, cur.y + dirs.y * j);
                        if (chTile.getClass() == resClass) {
                            preresults.set(i, preresults.get(i) + (5 - j) * weights.get(i));
                        }
                    }
                    if (type == 1) {
                        Tile chTile = cur.homeMap.getTile(cur.x + dirs.x * j, cur.y + dirs.y * j);
                        if (chTile.getClass() != resClass) {
                            preresults.set(i, preresults.get(i) + (5 - j) * weights.get(i));
                        }
                    }
                    if (type == 2) {
                        CreatureMap curMap = owner.getLocation();
                        Creature chTile = curMap.getCreature(cur.x + dirs.x * j, cur.y + dirs.y * j);
                        if (chTile != null) {
                            if (chTile.getClass() == owner.getClass()) {
                                preresults.set(i, preresults.get(i) + (5 - j) * weights.get(i));
                            }
                        }
                    }

                    if (type == 3) {
                        CreatureMap curMap = owner.getLocation();
                        Creature chTile = curMap.getCreature(cur.x + dirs.x * j, cur.y + dirs.y * j);
                        if (chTile != null) {
                            if ((chTile.getClass() != owner.getClass()) && (chTile.getClass() != resClass)) {
                                preresults.set(i, preresults.get(i) + (5 - j) * weights.get(i));
                            }
                        }
                    }
                    k++;
                }
                j++;
            }
            i++;
        }
        preresults.add(owner.getLifetime() * weights.get(5));
        preresults.add(owner.getHunger() * weights.get(6));
        int k = 0;
        while (k + 7 < wl) {
            preresults.add(owner.getMemory(k) * weights.get(k + 7));
            k++;
        }
        Double mulSum = 0.0;
        List<Double> inputsOfLevel = new ArrayList<>();
        i = 0;
        while (i < preresults.size()) {
            mulSum += preresults.get(i);
            inputsOfLevel.add(preresults.get(i));
            i++;
        }
        res = mulSum;
        int h = 0;
        while (h < c_weights.size()) {
            List<Double> inputsReplic = new ArrayList<>();
            int h_size = c_weights.get(h).size();
            double tmp = 0.0;
            int l = 0;
            while (l < h_size) {
                i = 0;
                tmp = 0.0;
                while (i < 2) {
                    tmp += inputsOfLevel.get(((i - 1) + h_size) % h_size) * c_weights.get(h).get(l);
                    i++;
                }
                inputsReplic.add(tmp);
                l++;
            }
            inputsOfLevel = inputsReplic;
            h++;
        }
        k = 0;
        while (k + 7 < wl) {
            owner.setMemory(k, res * weights2.get(k + 7));
            k++;
        }
        i = 0;
        res = 0;
        while (i < inputsOfLevel.size()) {
            res += inputsOfLevel.get(i);
            i++;
        }
        return res;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public Double getWeight(int i) {
        return weights.get(i);
    }

    public Double getWeight2(int i) {
        return weights2.get(i);
    }

    public Brain crossover(Brain other, Creature newOwner) {
        List<Double> newWeights = new ArrayList<>();
        List<Double> newWeights2 = new ArrayList<>();
        int i = 0;
        Random r = new Random();
        while (i < weights.size()) {
            if (r.nextBoolean()) {
                newWeights.add(weights.get(i));
            } else newWeights.add(other.getWeight(i));

            if (r.nextBoolean()) {
                newWeights2.add(weights2.get(i));
            } else newWeights2.add(other.getWeight2(i));
            i++;
        }
        Brain newBrain = new Brain(type, resClass);
        newBrain.weights = newWeights;
        newBrain.weights2 = newWeights2;
        return newBrain;
    }

    protected Brain InitBrain() {
        return (new Brain(type, resClass));
    }

    public Brain mutCopy() {
        Brain newBrain = InitBrain();
        Random r = new Random();
        int i = 0;
        while (i < wl) {
            newBrain.weights.set(i, weights.get(i) + r.nextDouble() * 0.2 - 0.1);
            newBrain.weights2.set(i, weights2.get(i) + r.nextDouble() * 0.2 - 0.1);
            int h = 0;
            while (h < c_weights.size()) {
                newBrain.c_weights.get(h).set(i, c_weights.get(h).get(i) + r.nextDouble() * 0.2 - 0.1);
                h++;
            }
            i++;
        }
        return newBrain;
    }
}
