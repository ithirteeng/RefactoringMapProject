package com.example.oopmap.Tiles;

import com.example.oopmap.Meta.TileMap;

public class RiverTile extends WaterTile {
    public RiverTile(TileMap map, Integer tx, Integer ty, double waterLevel) {
        super(map, tx, ty, waterLevel);
        color.setARGB(255, 10, 100, 255);
    }

    @Override
    public Tile updateTile() {
        if (getLevel() > 0) {
            return super.updateTile();
        }
        return this;
    }
}
