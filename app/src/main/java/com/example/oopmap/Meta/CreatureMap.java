package com.example.oopmap.Meta;

import com.example.oopmap.Crits.Creature;
import com.example.oopmap.Crits.Human;
import com.example.oopmap.Crits.Predator;
import com.example.oopmap.Crits.PredatorBeta;
import com.example.oopmap.Crits.PredatorGamma;
import com.example.oopmap.Crits.VegBeta;
import com.example.oopmap.Crits.VegCreature;
import com.example.oopmap.Crits.VegGamma;
import com.example.oopmap.Tiles.GrassTile;
import com.example.oopmap.Tiles.Tile;

import java.util.Random;

public class CreatureMap {
    private final Creature[][] creatures = new Creature[LProvider.MAX_SIZE][LProvider.MAX_SIZE];
    private final TileMap tiles;
    private Integer creatureAmount = 0;
    private final PheromoneMap phs = new PheromoneMap();

    public CreatureMap(TileMap loc) {
        tiles = loc;
        int i = 0;
        while (i < LProvider.MAX_SIZE) {
            int j = 0;
            while (j < LProvider.MAX_SIZE) {
                creatures[i][j] = null;
                j++;
            }
            i++;
        }
        Thread ph = new Thread(new Runnable() {
            @Override
            public void run() {
                phs.update();
            }
        });
        ph.start();
    }

    public void addCreature(Creature creature) {
        creatures[creature.getCurrentTile().x][creature.getCurrentTile().y] = creature;
        creature.bindPhs(phs);
        creatureAmount++;
    }

    public Creature getCreature(int x, int y) {
        return creatures[(x + LProvider.MAX_SIZE) % LProvider.MAX_SIZE][(y + LProvider.MAX_SIZE) % LProvider.MAX_SIZE];
    }

    public void kill(int x, int y) {
        creatures[x][y] = null;
        creatureAmount--;
    }

    private void addRandomCreature() {
        Random r = new Random();
        Tile target = tiles.getTile(r.nextInt(LProvider.MAX_SIZE), r.nextInt(LProvider.MAX_SIZE));
        switch (r.nextInt(9)) {
            case 1: {
                VegCreature newCreature = new VegCreature(target, this);
                creatures[newCreature.getCurrentTile().x][newCreature.getCurrentTile().y] = newCreature;
                newCreature.bindPhs(phs);
                newCreature.setPrefferedFood(GrassTile.class);
                break;
            }
            case 2: {
                Predator newPredator = new Predator(target, this);
                creatures[newPredator.getCurrentTile().x][newPredator.getCurrentTile().y] = newPredator;
                newPredator.bindPhs(phs);
                break;
            }
            case 3:
            case 7:
            case 8: {
                Human newHuman = new Human(target, this);
                creatures[newHuman.getCurrentTile().x][newHuman.getCurrentTile().y] = newHuman;
                newHuman.bindPhs(phs);
                break;
            }
            case 4: {
                VegBeta newCreature = new VegBeta(target, this);
                creatures[newCreature.getCurrentTile().x][newCreature.getCurrentTile().y] = newCreature;
                newCreature.bindPhs(phs);
                newCreature.setPrefferedFood(GrassTile.class);
                break;
            }
            case 5: {
                VegGamma newCreature = new VegGamma(target, this);
                creatures[newCreature.getCurrentTile().x][newCreature.getCurrentTile().y] = newCreature;
                newCreature.bindPhs(phs);
                newCreature.setPrefferedFood(GrassTile.class);
                break;
            }
            case 6: {
                PredatorBeta newPredator = new PredatorBeta(target, this);
                creatures[newPredator.getCurrentTile().x][newPredator.getCurrentTile().y] = newPredator;
                newPredator.bindPhs(phs);
                break;
            }
            default: {
                PredatorGamma newPredator = new PredatorGamma(target, this);
                creatures[newPredator.getCurrentTile().x][newPredator.getCurrentTile().y] = newPredator;
                newPredator.bindPhs(phs);
                break;
            }
        }
        creatureAmount++;
    }

    public void update() {
        if (creatureAmount < 60) {
            addRandomCreature();
        }
        creatureAmount = 0;
        int i = 0;
        while (i < LProvider.MAX_SIZE) {
            int j = 0;
            while (j < LProvider.MAX_SIZE) {
                if (creatures[i][j] != null)
                    if (!creatures[i][j].analized) {
                        creatures[i][j].analized = true;
                        creatures[i][j].update();
                        creatureAmount++;
                    }
                j++;
            }
            i++;
        }

        i = 0;
        while (i < LProvider.MAX_SIZE) {
            int j = 0;
            while (j < LProvider.MAX_SIZE) {
                if (creatures[i][j] != null)
                    creatures[i][j].analized = false;
                j++;
            }
            i++;
        }
    }
}
