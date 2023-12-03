package com.example.oopmap.Tiles;

import com.example.oopmap.Meta.TileMap;
import com.example.oopmap.Tiles.Tile;

public class WaterTile extends Tile {

    private double waterLevel;

    public WaterTile(TileMap map, Integer tx, Integer ty, double waterLevel) {
        super(map, tx, ty);
        this.waterLevel=waterLevel;
        color.setARGB(255,0,20,100);
    }

    @Override
    public Tile updateTile() {
        if (getLevel()>0)
        {
            return super.updateTile();
        }
        int count=0;
        int i=0;
        while (i<3)
        {
            int j=0;
            while (j<3)
            {
                if (homeMap.getTile(x+i-1,y+j-1).getClass()!=this.getClass())
                {
                    count++;
                }
                j++;
            }
            i++;
        }
        if ((count>4))
        {
            return this.makeRiver();
        }
        else return this;
    }
}
