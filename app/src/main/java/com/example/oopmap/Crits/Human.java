package com.example.oopmap.Crits;

import com.example.oopmap.Meta.BestBrains;
import com.example.oopmap.Meta.CreatureMap;
import com.example.oopmap.Meta.DirVector;
import com.example.oopmap.Tiles.BuildingTile;
import com.example.oopmap.Tiles.ForestTile;
import com.example.oopmap.Tiles.GrassTile;
import com.example.oopmap.Tiles.Tile;
import com.example.oopmap.Tiles.WallTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Human extends Creature {
    private int invSize = 0;

    public Human(Tile start, CreatureMap location) {
        super(start, location);
        Random r = new Random();
        PrefferedFood = GrassTile.class;
        if ((BestBrains.hasClass(this.getClass())) && (r.nextDouble() > 0.2)) {
            int i = 0;
            while (i < 6) {
                brains.add(i, ((HumanBrain) BestBrains.getBest(this.getClass()).get(i)).mutCopy());
                i++;
            }
        } else {
            brains.add(new HumanBrain(0));
            brains.add(new HumanBrain(1));
            brains.add(new HumanBrain(2));
            brains.add(new HumanBrain(3));
            brains.add(new HumanBrain(4));
            brains.add(new HumanBrain(5));
        }
        PrefferedTile = GrassTile.class;
        int i = 0;
        while ((i + 8) < 12) {
            //  addMemoryCell();
            i++;
        }
        addMemoryCell();

        invSize += 6;
        setHunger(getHunger());
        setColor(255, 255, 0);
        type = 6;
    }

    public Human(Creature otherCreature1, Creature otherCreature2) {
        super(otherCreature1, otherCreature2);
        int i = 0;
        while ((i + 8) < 12) {
            //     addMemoryCell();
            i++;
        }

        addMemoryCell();
        setHunger(getHunger());
        setColor(255, 255, 0);
        invSize += 6;
        type = 6;
    }

    public int getInvSize() {
        return invSize;
    }

    @Override
    protected void StepConsumesAndChecks() {
        DirVector dirVector = new DirVector(direction);
        if (location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y) != null) {
            if (location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y).getClass() != this.getClass()) {
                location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y).eatTh();
                location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y).update();
                setHunger(getHunger() + 100 * getFoodMp());
                phs.increaseBy(currentTile.x, currentTile.y, type, 100);
            }
        }
        if (currentTile.getClass() == ForestTile.class) {
            currentTile.homeMap.changeTile(currentTile.makeEaten());
        }
    }

    @Override
    protected void MakeChild() {
        DirVector dirVector = new DirVector(direction);
        Human newborn = new Human(this, location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y));
        newborn.currentTile = currentTile.homeMap.getTile(currentTile.x - dirVector.x, currentTile.y - dirVector.y);
        setHunger(getHunger() - 25);
        int k = 0;
        while (k < brains.size()) {
            newborn.brains.add(brains.get(k).crossover(
                    location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y).brains.get(k),
                    newborn
            ));
            k++;
        }
        newborn.location = location;
        location.addCreature(newborn);
        childAmount++;
    }

    @Override
    protected void act() {
        double thoght = 0.0;
        List<Double> res = new ArrayList<>();
        int dvd = 6;
        double tmp;
        int i = 0;
        while (i < 6) {
            tmp = brains.get(i).getResult(this) * 12;
            tmp = (tmp - Math.floor(tmp)) + ((int) Math.floor(tmp)) % 12;
            res.add(tmp);
            if (tmp > 9) {
                thoght += tmp;
                dvd++;
            }

            if (tmp > 10) {
                thoght += tmp;
                dvd++;
            }
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
        thoght = thoght / dvd;
        int mini = 0;
        tmp = Math.abs(res.get(0) - thoght);
        if (thoght < 8) {
            thoght = (thoght * 5 + minp) / 6;
        }
        while (i < 6) {
            if ((Math.abs(res.get(i) - thoght)) < tmp) {
                mini = i;
                tmp = Math.abs(res.get(i) - thoght);
            }
            i++;
        }
        thoght = res.get(mini);
        switch (((int) Math.floor(thoght)) % 12) {
            case 8: {
                direction--;
                break;
            }
            case 9: {
                direction++;
                break;
            }
            case 10: {
                if (invSize > 0) {
                    currentTile.makeBuilding(1);
                    invSize--;
                }
                break;
            }
            case 11: {
                if (invSize > 0) {
                    currentTile.makeBuilding(2);
                    invSize--;
                }
                break;
            }
            default: {
                DirVector vec = new DirVector(direction + ((int) Math.floor(thoght)) % 11);
                if ((location.getCreature(currentTile.x + vec.x, currentTile.y + vec.y) == null) && (
                        currentTile.homeMap.getTile(currentTile.x + vec.x, currentTile.y + vec.y).getClass() != WallTile.class)) {
                    currentTile = currentTile.homeMap.getTile(currentTile.x + vec.x, currentTile.y + vec.y);
                }
                break;
            }
        }
    }

    @Override
    protected void tilePenalty() {
        super.tilePenalty();
        if (currentTile.getClass() == BuildingTile.class) {
            if (((BuildingTile) currentTile).getLifetime() < 800) {
                setHunger(getHunger() + 100);
                currentTile.homeMap.changeTile(currentTile.makeEaten());
            }
        }
    }

    @Override
    protected void addBr() {
        BestBrains.addBrain(this, this.getClass());
        location = null;
    }
}
