package com.example.oopmap.Crits;

import android.graphics.Paint;

import com.example.oopmap.Meta.BestBrains;
import com.example.oopmap.Meta.CreatureMap;
import com.example.oopmap.Meta.DirVector;
import com.example.oopmap.Meta.PheromoneMap;
import com.example.oopmap.Tiles.Tile;
import com.example.oopmap.Tiles.WallTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Creature {
    protected Class PrefferedFood = null;
    protected Class PrefferedTile = null;
    private double hunger = 50;
    protected List<Brain> brains = new ArrayList<>();
    protected Tile currentTile;
    int lifetime;
    protected int direction;
    protected CreatureMap location;
    public boolean analized = false;
    protected int childAmount = 0;
    private final List<Double> memory = new ArrayList<>();
    private double foodMp = 1;
    private final Paint color = new Paint();
    protected PheromoneMap phs;
    protected int type;
    private boolean died = false;

    public Double getMemory(int i) {
        return memory.get(i);
    }

    public void setMemory(int pos, Double mem) {
        memory.set(pos, mem);
    }

    protected void addMemoryCell() {
        memory.add(0.0);
    }

    public Creature(Tile start, CreatureMap location) {
        this.location = location;

        currentTile = start;
        hunger = 50;
        lifetime = 0;
        Random r = new Random();
        direction = r.nextInt(9);

    }

    public void bindPhs(PheromoneMap phs) {
        this.phs = phs;
    }

    public PheromoneMap getPhs() {
        return phs;
    }

    public Creature(Creature otherCreature1, Creature otherCreature2) {
        currentTile = otherCreature1.currentTile;
        Random r = new Random();
        if (r.nextBoolean()) {
            PrefferedFood = otherCreature1.PrefferedFood;
            PrefferedTile = otherCreature2.PrefferedTile;
        } else {
            PrefferedFood = otherCreature2.PrefferedFood;
            PrefferedTile = otherCreature1.PrefferedTile;
        }
        hunger = 50;
        lifetime = 0;
        direction = r.nextInt(9);
        location = otherCreature1.location;
        bindPhs(otherCreature1.getPhs());
    }

    public int getLifetime() {
        return lifetime;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public CreatureMap getLocation() {
        return location;
    }

    public double getHunger() {
        return hunger;
    }

    public void setHunger(double hunger) {
        this.hunger = hunger;
    }

    protected void StepConsumesAndChecks() {
        if (currentTile.getClass() == PrefferedFood) {
            currentTile.homeMap.changeTile(currentTile.makeEaten());
            hunger += 10 * foodMp;
            phs.increaseBy(currentTile.x, currentTile.y, type, 50);
        }
    }

    protected void MakeChild() {
/*
        DirVector dirVector=new DirVector(direction);
        Creature newborn = new Creature(this, location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y));
        newborn.currentTile = currentTile.homeMap.getTile(currentTile.x - dirVector.x, currentTile.y - dirVector.y);
        hunger-=50;
        int k=0;
        while (k<brains.size())
        {
            newborn.brains.add(brains.get(k).crossover(
                    location.getCreature(currentTile.x+dirVector.x,currentTile.y+dirVector.y).brains.get(k),
                    newborn
            ));
            k++;
        }
        childAmount++;*/
    }

    public void update() {
        if (location != null) {
            location.kill(currentTile.x, currentTile.y);
        }
        act();

        tilePenalty();

        StepConsumesAndChecks();

        DirVector dirVector = new DirVector(direction);
        if (location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y) != null)
            if (location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y).getClass() == this.getClass()) {
                if (hunger > 50) {
                    MakeChild();
                }
            }

        hunger--;

        lifetime++;

        if ((hunger > 0) && (!died)) {
            location.addCreature(this);
            phs.increaseBy(currentTile.x, currentTile.y, type, 1);
        } else {
            phs.increaseBy(currentTile.x, currentTile.y, type, -400);
            addBr();
        }
    }


    protected void addBr() {
        BestBrains.addBrain(this, this.getClass());
        location = null;
    }


    protected void tilePenalty() {
        if (currentTile.getClass() != PrefferedTile) {
            hunger--;
        }
    }

    protected void act() {
        Double thoght = 0.0;
        List<Double> res = new ArrayList<>();
        double tmp;
        int i = 0;
        while (i < 4) {
            tmp = brains.get(i).getResult(this) * 10;
            tmp = (tmp - Math.floor(tmp)) + ((int) Math.floor(tmp)) % 10;
            res.add(tmp);
            thoght += tmp;
            i++;
        }
        double mph = 0.0;
        int minp = 0;
        i = 0;
        while (i < 8) {
            double curph;
            DirVector next = new DirVector(direction + i);
            curph = getPhs().getPheromones(currentTile.x + next.x, currentTile.y + next.y, type);
            if ((i == 0) || (curph > mph)) {
                mph = curph;
                minp = i;
            }
            i++;
        }
        i = 0;
        thoght = thoght / 4;

        int mini = 0;
        tmp = Math.abs(res.get(0) - thoght);
        while (i < 4) {
            if ((Math.abs(res.get(i) - thoght)) < tmp) {
                mini = i;
                tmp = Math.abs(res.get(i) - thoght);
            }
            i++;
        }
        thoght = res.get(mini);
        if (thoght < 8) {
            thoght = (thoght * 2 + minp) / 3;
        }
        switch (((int) Math.floor(thoght)) % 10) {
            case 8: {
                direction--;
                break;
            }
            case 9: {
                direction++;
                break;
            }
            default: {
                DirVector vec = new DirVector(direction + ((int) Math.floor(thoght)) % 10);
                if ((location.getCreature(currentTile.x + vec.x, currentTile.y + vec.y) == null) && (
                        currentTile.homeMap.getTile(currentTile.x + vec.x, currentTile.y + vec.y).getClass() != WallTile.class)) {
                    currentTile = currentTile.homeMap.getTile(currentTile.x + vec.x, currentTile.y + vec.y);
                }
                break;
            }
        }
    }

    public void setPrefferedFood(Class prefferedFood) {
        this.PrefferedFood = prefferedFood;
    }

    public List<Brain> getBrains() {
        return brains;
    }

    public int getChildAmount() {
        return childAmount;
    }

    protected void setFoodMp(double foodMp) {
        this.foodMp = foodMp;
    }

    public double getFoodMp() {
        return foodMp;
    }

    protected void setColor(int a, int b, int c) {
        color.setARGB(255, a, b, c);
    }

    public Paint getColor() {
        return color;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public void eatTh() {
        died = true;
    }
}
