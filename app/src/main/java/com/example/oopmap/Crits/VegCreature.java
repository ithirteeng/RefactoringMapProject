package com.example.oopmap.Crits;

import com.example.oopmap.Crits.Brain;
import com.example.oopmap.Crits.Creature;
import com.example.oopmap.Meta.BestBrains;
import com.example.oopmap.Meta.CreatureMap;
import com.example.oopmap.Meta.DirVector;
import com.example.oopmap.Tiles.GrassTile;
import com.example.oopmap.Tiles.Tile;

import java.util.Random;

public class VegCreature extends Creature {
    public VegCreature(Tile start, CreatureMap location) {
        super(start, location);
        Random r = new Random();
        PrefferedFood = GrassTile.class;
        if ((BestBrains.hasClass(this.getClass())) && (r.nextDouble() > 0.2)) {
            int i = 0;
            while (i < 4) {
                brains.add(i, BestBrains.getBest(this.getClass()).get(i).mutCopy());
                i++;
            }
        } else {
            brains.add(new Brain(0, GrassTile.class));
            brains.add(new Brain(1, GrassTile.class));
            brains.add(new Brain(2, this.getClass()));
            brains.add(new Brain(3, this.getClass()));
        }
        PrefferedTile = GrassTile.class;
        addMemoryCell();
        setColor(255,255,255);
        type=0;
    }

    public VegCreature(Creature otherCreature1, Creature otherCreature2) {
        super(otherCreature1, otherCreature2);
        addMemoryCell();
        setColor(255,255,255);
        type=0;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    protected void MakeChild() {
        DirVector dirVector=new DirVector(direction);
        VegCreature newborn = new VegCreature(this, location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y));
        newborn.currentTile = currentTile.homeMap.getTile(currentTile.x - dirVector.x, currentTile.y - dirVector.y);
        setHunger(getHunger()-25);
        int k=0;
        while (k<brains.size())
        {
            newborn.brains.add(brains.get(k).crossover(
                    location.getCreature(currentTile.x+dirVector.x,currentTile.y+dirVector.y).brains.get(k),
                    newborn
            ));
            k++;
        }
        newborn.location=location;
        location.addCreature(newborn);
        childAmount++;
    }
}
