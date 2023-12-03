package com.example.oopmap.Crits;

import com.example.oopmap.Meta.CreatureMap;
import com.example.oopmap.Meta.DirVector;
import com.example.oopmap.Tiles.BuildingTile;
import com.example.oopmap.Tiles.Tile;
import com.example.oopmap.Tiles.WallTile;

import java.util.ArrayList;
import java.util.List;

public class HumanBrain extends PredatorBrain {
    public HumanBrain(int type) {
        super(type);
    }

    @Override
    protected void zeroSetup()
    {
        wl=9;
    }

    @Override
    public double getResult(Creature owner) {
        double res=0;
        int i=0;
        List<Double> preresults=new ArrayList<>();
        List<Double> resultssum=new ArrayList<>();
        while (i<5)
        {
            int j=0;
            DirVector dirs=new DirVector(owner.getDirection()+i);
            DirVector dirsCo=new DirVector(owner.getDirection()+i+2);
            preresults.add(0.0);
            while (j<5)
            {
                int k=-1;
                while (k<2) {
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
                    if (type == 4) {
                        Tile chTile = cur.homeMap.getTile(cur.x + dirs.x * j, cur.y + dirs.y * j);
                        if (chTile.getClass() == BuildingTile.class) {
                            preresults.set(i, preresults.get(i) + (5 - j) * weights.get(i));
                        }
                    }
                    if (type == 5) {
                        Tile chTile = cur.homeMap.getTile(cur.x + dirs.x * j, cur.y + dirs.y * j);
                        if (chTile.getClass() == WallTile.class) {
                            preresults.set(i, preresults.get(i) + (5 - j) * weights.get(i));
                        }
                    }
                    k++;
                }
                    j++;
                }

            i++;
            }
        preresults.add(owner.getLifetime()*weights.get(5));
        preresults.add(owner.getHunger()*weights.get(6));
        preresults.add(((Human)owner).getInvSize()*weights.get(7));
        int k=0;
        while (k+8<wl)
        {
            preresults.add(owner.getMemory(k)*weights.get(k+8));
            k++;
        }
        Double mulSum=0.0;
        i=0;
        while (i<preresults.size())
        {
            mulSum+=preresults.get(i);
            i++;
        }
        i=0;
        res=mulSum;
        int h=0;
        while (h<c_weights.size()) {
            double tmp=0.0;
            i = 0;
            while (i < c_weights.get(h).size()) {
                tmp += res * c_weights.get(h).get(i);
                i++;
            }
            res=tmp;
            h++;
        }
        k=0;
        while (k+8<wl)
        {
            owner.setMemory(k,res*weights2.get(k+8));
            k++;
        }

        return res;
    }

    @Override
    protected Brain InitBrain()
    {
        return (new HumanBrain(type));
    }
}
