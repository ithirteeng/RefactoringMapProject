package com.example.oopmap.Crits;

import com.example.oopmap.Meta.BestBrains;
import com.example.oopmap.Meta.CreatureMap;
import com.example.oopmap.Meta.DirVector;
import com.example.oopmap.Tiles.GrassTile;
import com.example.oopmap.Tiles.Tile;

import java.util.Random;

public class Predator extends Creature {
    public Predator(Tile start, CreatureMap location) {
        super(start, location);
        Random r = new Random();
        PrefferedFood = GrassTile.class;
        if ((BestBrains.hasClass(this.getClass())) && (r.nextDouble() > 0.2)) {
            int i = 0;
            while (i < 4) {
                brains.add(i, ((PredatorBrain) BestBrains.getBest(this.getClass()).get(i)).mutCopy());
                i++;
            }
        } else {
            brains.add(new PredatorBrain(0));
            brains.add(new PredatorBrain(1));
            brains.add(new PredatorBrain(2));
            brains.add(new PredatorBrain(3));
        }
        PrefferedTile = GrassTile.class;
        //   addMemoryCell();
        addMemoryCell();
        setHunger(getHunger());
        setColor(255, 0, 0);
        type = 3;
    }

    public Predator(Creature otherCreature1, Creature otherCreature2) {
        super(otherCreature1, otherCreature2);
        addMemoryCell();
        //  addMemoryCell();
        setHunger(getHunger());
        setColor(255, 0, 0);
        type = 3;
    }

    @Override
    protected void StepConsumesAndChecks() {
        DirVector dirVector = new DirVector(direction);
        if (location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y) != null) {
            if (location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y).getClass() != this.getClass()) {
                location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y).eatTh();
                location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y).update();
                setHunger(getHunger() + 100 * getFoodMp());
                phs.increaseBy(currentTile.x, currentTile.y, type, 10);
            }
        }
    }

    @Override
    protected void MakeChild() {
        DirVector dirVector = new DirVector(direction);
        Predator newborn = new Predator(this, location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y));
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
    public void update() {
        super.update();
    }

    @Override
    protected void addBr() {
        BestBrains.addBrain(this, this.getClass());
        location = null;
    }
}
