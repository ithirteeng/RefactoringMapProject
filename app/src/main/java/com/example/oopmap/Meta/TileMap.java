package com.example.oopmap.Meta;


import com.example.oopmap.Tiles.RiverTile;
import com.example.oopmap.Tiles.Tile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TileMap {
    private Tile[][] map=new Tile[LProvider.MAX_SIZE][LProvider.MAX_SIZE];

    public TileMap()
    {
        double levelDiff=0;
        int i=0;
        while (i<LProvider.MAX_SIZE){
            int j=0;
            while (j<LProvider.MAX_SIZE)
            {
                map[i][j]=new Tile(this,i,j);
                levelDiff+=map[i][j].getLevel();
                j++;
            }
            i++;
        }
        getTile(LProvider.MAX_SIZE,LProvider.MAX_SIZE).setLevel(getTile(LProvider.MAX_SIZE,LProvider.MAX_SIZE).getLevel()-levelDiff);
        i=0;
        while (i<6) {
            this.normalizeMap();
            i++;
        }
        updateMap();
        Random r=new Random();
        i=8;
        while (i>0)
        {
            int sx=r.nextInt(LProvider.MAX_SIZE);
            int sy=r.nextInt(LProvider.MAX_SIZE);
            int fx=sx+r.nextInt(20)-10;
            int fy=sy+r.nextInt(20)-10;
            double flevel=getTile(fx,fy).getLevel();
            int dir=1;
            double llevel=flevel;
            if (getTile(sx,sy).getLevel()<llevel)
            {
                llevel=getTile(sx,sy).getLevel();
            }
            if (llevel>0)
            {
                flevel+=100;
            }
            if (flevel>getTile(sx,sy).getLevel())
            {
                dir=-dir;
            }
            List<Tile> newTmp= AStard(getTile(sx,sy),getTile(fx,fy),dir);
            int u=0;
            while (u<newTmp.size())
            {
                Tile tile=newTmp.get(u);
                if ((tile.getLevel()>0)&&(llevel<0))
                {
                    RiverTile newTile=tile.makeRiver();
                    newTile.setLevel(flevel/2);
                    map[tile.x][tile.y] = newTile;
                }
                else {
                    tile.setLevel((tile.getLevel() + flevel) / 2);
                }
                u++;
            }
            i--;
        }
        while (i<10) {
            this.normalizeMap();
            i++;
        }
       //updateMap();
    }

    public Tile getTile(Integer x,Integer y)
    {
        return map[(x+LProvider.MAX_SIZE)%LProvider.MAX_SIZE][(y+LProvider.MAX_SIZE)%LProvider.MAX_SIZE];
    }

    public void normalizeMap()
    {
        Tile[][] newMap=new Tile[LProvider.MAX_SIZE][LProvider.MAX_SIZE];
        int i=0;
        while (i<LProvider.MAX_SIZE){
            int j=0;
            while (j<LProvider.MAX_SIZE)
            {
                newMap[i][j]=map[i][j].normalize();
                j++;
            }
            i++;
        }
        map=newMap;

    }

    private List<Tile> AStard(Tile beg, final Tile end,int dir) {
        List<Tile> newList = new ArrayList<>();
        List<Tile> queue = new ArrayList<>();
        queue.add(beg);
        int am=0;
        double bestWay = LProvider.MAX_SIZE*LProvider.MAX_SIZE;
        Tile cur = queue.get(0);
        while ((!queue.isEmpty()) && ((end.getAstarCost() == 0) || ((dir * cur.getLevel()) < end.getLevel()))&&(am<500)) {
            cur = queue.get(0);
            queue.remove(0);
            if (cur.dist(end) < 50) {
                int i = -1;
                while (i < 2) {
                    int j = -1;
                    while (j < 2) {
                        if ((j + i + 2) % 2 == 1) {
                            if (!newList.contains(this.getTile(cur.x + i, cur.y + j))) {
                                this.getTile(cur.x + i, cur.y + j).setAstarCost(cur.getAstarCost() + cur.getLevel() - this.getTile(cur.x + i, cur.y + j).getLevel());
                                queue.add(this.getTile(cur.x + i, cur.y + j));
                                am++;
                                newList.add(this.getTile(cur.x + i, cur.y + j));
                            }
                        }
                        j++;
                    }
                    i++;
                }
            }
            final int innerDir = dir;
            Comparator<Tile> comparator = new Comparator<Tile>() {
                public int compare(Tile o1, Tile o2) {
                    return innerDir * ((Double) (o1.getAstarCost() + o1.getLevel() - end.getLevel()
                    )).compareTo(
                            (Double) (o2.getAstarCost() + o2.getLevel() - end.getLevel())
                    );
                }
            };

            queue.sort(comparator);
        }

        return newList;
    }

    public void updateMap()
    {
        Tile[][] newMap=new Tile[LProvider.MAX_SIZE][LProvider.MAX_SIZE];
        int i=0;
        while (i<LProvider.MAX_SIZE){
            int j=0;
            while (j<LProvider.MAX_SIZE)
            {
                newMap[i][j]=map[i][j].updateTile();
                j++;
            }
            i++;
        }
        map=newMap;
    }

    public void changeTile(Tile newTile)
    {
        map[newTile.x][newTile.y]=newTile;
    }

}
