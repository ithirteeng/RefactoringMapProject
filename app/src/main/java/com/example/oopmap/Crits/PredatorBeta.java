package com.example.oopmap.Crits;

import com.example.oopmap.Meta.BestBrains;
import com.example.oopmap.Meta.CreatureMap;
import com.example.oopmap.Meta.DirVector;
import com.example.oopmap.Tiles.Tile;

public class PredatorBeta extends Predator {
    public PredatorBeta(Tile start, CreatureMap location) {
        super(start, location);
        setFoodMp(2);
        setColor(255, 150, 0);
        type = 4;
    }

    public PredatorBeta(Creature otherCreature1, Creature otherCreature2) {
        super(otherCreature1, otherCreature2);
        setFoodMp(2);
        setColor(255, 150, 0);
        type = 4;
    }

    @Override
    protected void tilePenalty() {
        if (currentTile.getClass() != PrefferedTile) {
            setHunger(getHunger() - 2);
        }
    }

    @Override
    protected void addBr() {
        BestBrains.addBrain(this, this.getClass());
        location = null;
    }

    @Override
    protected void MakeChild() {
        DirVector dirVector = new DirVector(direction);
        PredatorBeta newborn = new PredatorBeta(this, location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y));
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
}
