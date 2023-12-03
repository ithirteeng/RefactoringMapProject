package com.example.oopmap.Tiles;

import com.example.oopmap.Meta.TileMap;

public class WallTile extends BuildingTile {

    public WallTile(TileMap map, Integer tx, Integer ty) {
        super(map, tx, ty);
        setLifetime(2000);
        color.setARGB(255, 255, 0, 0);
    }

}
