package com.example.oopmap.Tiles;

import android.graphics.Paint;

import com.example.oopmap.Meta.TileMap;
import com.example.oopmap.Meta.TrueRand;

import java.util.Random;

public class Tile {
    private double level;
    public TileMap homeMap;
    public Integer x, y;
    private double AstarCost = 0;
    protected Paint color = new Paint();
    protected int builded = 0;

    public Tile(TileMap map, Integer tx, Integer ty) {
        Random r = new Random();
        level = r.nextGaussian() * 50;
        homeMap = map;
        x = tx;
        y = ty;
        color.setARGB(255, 100, 50, 0);
    }

    public Paint getColor() {
        return color;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public Tile normalize() {
        Tile tmp = new Tile(homeMap, x, y);
        tmp.setLevel((level * 4
                + homeMap.getTile(x, y - 1).getLevel()
                + homeMap.getTile(x - 1, y).getLevel()
                + homeMap.getTile(x + 1, y).getLevel()
                + homeMap.getTile(x, y + 1).getLevel()) / 8);
        return tmp;
    }

    public Tile updateTile() {
        Random r = new Random();
        if (level < 0) {
            return this.watered();
        }
        if (level < 20) {
            if (TrueRand.random.nextBoolean())
                return this.withGrass();
            return this.withForest();
        }
        if (level > 60) {
            color.setARGB(255, 255, 255, 255);
        }
        if (builded == 1) {
            BuildingTile newTile = new BuildingTile(homeMap, x, y);
            newTile.setLevel((level + 20) / 2);
            return newTile;
        }
        if (builded == 2) {
            WallTile newTile = new WallTile(homeMap, x, y);
            newTile.setLevel((level + 20) / 2);
            return newTile;
        }
        return this;
    }

    public Tile watered() {
        WaterTile newTile = new WaterTile(homeMap, x, y, 1);
        newTile.setLevel(level);
        return newTile;
    }

    public Tile withGrass() {
        GrassTile newTile = new GrassTile(homeMap, x, y);
        newTile.setLevel(level);
        return newTile;
    }

    public Tile withForest() {
        ForestTile newTile = new ForestTile(homeMap, x, y);
        newTile.setLevel(level);
        return newTile;
    }

    public double getAstarCost() {
        return AstarCost;
    }

    public void setAstarCost(double astarCost) {
        AstarCost = astarCost;
    }

    public RiverTile makeRiver() {
        RiverTile newTile = new RiverTile(homeMap, x, y, 1);
        newTile.setLevel(level);
        return newTile;
    }

    public EatenTile makeEaten() {
        EatenTile newTile = new EatenTile(homeMap, x, y);
        newTile.setLevel(level);
        return newTile;
    }


    public double dist(Tile tile) {
        return Math.sqrt((x - tile.x) * (x - tile.x) + (y - tile.y) * (y - tile.y));
    }

    public void makeBuilding(int index) {
        builded = index;
    }
}
