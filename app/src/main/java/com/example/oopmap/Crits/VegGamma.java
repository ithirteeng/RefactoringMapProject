package com.example.oopmap.Crits;

import com.example.oopmap.Meta.BestBrains;
import com.example.oopmap.Meta.CreatureMap;
import com.example.oopmap.Meta.DirVector;
import com.example.oopmap.Tiles.Tile;

public class VegGamma extends VegCreature {
    public VegGamma(Tile start, CreatureMap location) {
        super(start, location);
        setFoodMp(0.5);
        setColor(150,255,150);
        type=2;
    }

    public VegGamma(Creature otherCreature1, Creature otherCreature2) {
        super(otherCreature1, otherCreature2);
        setFoodMp(0.75);
        setColor(150,255,150);
        type=2;
    }

    @Override
    protected void tilePenalty() {
        if (currentTile.getClass()!=PrefferedTile)
        {
            setHunger(getHunger()-0.5);
        }
    }

    @Override
    protected void addBr()
    {
        BestBrains.addBrain(this,this.getClass());
        location=null;
    }

    @Override
    protected void MakeChild() {
        DirVector dirVector=new DirVector(direction);
        VegGamma newborn = new VegGamma(this, location.getCreature(currentTile.x + dirVector.x, currentTile.y + dirVector.y));
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
