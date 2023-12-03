package com.example.oopmap.Tiles;

import com.example.oopmap.Meta.TileMap;

public class GrassTile extends Tile {
    public GrassTile(TileMap map, Integer tx, Integer ty) {
        super(map, tx, ty);
        color.setARGB(255, 0, 200, 0);
    }

    @Override
    public Tile updateTile() {
        int count = 0;
        int i = 0;
        while (i < 3) {
            int j = 0;
            while (j < 3) {
                if (homeMap.getTile(x + i - 1, y + j - 1).getClass() == ForestTile.class) {
                    count++;
                }
                j++;
            }
            i++;
        }
        if (count == 3) {
            return this.withForest();
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
