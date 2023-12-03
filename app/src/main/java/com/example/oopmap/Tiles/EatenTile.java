package com.example.oopmap.Tiles;

import com.example.oopmap.Meta.TileMap;

public class EatenTile extends Tile {
    private long restoreTime = 0;

    public EatenTile(TileMap map, Integer tx, Integer ty) {
        super(map, tx, ty);
        color.setARGB(255, 200, 100, 0);
    }

    @Override
    public Tile updateTile() {
        restoreTime++;
        if (restoreTime > 200) {
            return this.withGrass();
        }
        if (builded == 1) {
            BuildingTile newTile = new BuildingTile(homeMap, x, y);
            newTile.setLevel((newTile.getLevel() + 20) / 2);
            return newTile;
        }
        if (builded == 2) {
            WallTile newTile = new WallTile(homeMap, x, y);
            newTile.setLevel((newTile.getLevel() + 20) / 2);
            return newTile;
        }
        return this;
    }
}
