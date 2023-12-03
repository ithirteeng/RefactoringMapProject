package com.example.oopmap.Tiles;

import com.example.oopmap.Meta.TileMap;

public class BuildingTile extends Tile {
    private int lifetime=1000;
    public BuildingTile(TileMap map, Integer tx, Integer ty) {
        super(map, tx, ty);
        color.setARGB(255,255,200,255);
    }

    protected void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public int getLifetime() {
        return lifetime;
    }

    @Override
    public Tile updateTile() {
        lifetime--;
        if (lifetime!=0)
            return this;
        else return this.makeEaten();
    }
}
