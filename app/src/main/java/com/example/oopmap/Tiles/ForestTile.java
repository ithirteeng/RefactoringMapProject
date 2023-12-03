package com.example.oopmap.Tiles;

import com.example.oopmap.Meta.TileMap;

public class ForestTile extends Tile {
    private int lifetime=0;

    public ForestTile(TileMap map, Integer tx, Integer ty) {
        super(map, tx, ty);
        color.setARGB(255,50,150,50);
    }

    public int getLifetime() {
        return lifetime;
    }

    @Override
    public Tile updateTile()
    {
        lifetime++;
        int count=-1;
        int i=0;
        while (i<3)
        {
            int j=0;
            while (j<3)
            {
                if (homeMap.getTile(x+i-1,y+j-1).getClass()==this.getClass())
                {
                    count++;
                }
                j++;
            }
            i++;
        }
        if (((count!=2)&&(count!=3)&&(lifetime>50))||(lifetime>200))
        {
            return this.withGrass();
        }
        else return this;
    }
}
