package com.example.oopmap.Tiles;

import com.example.oopmap.Meta.TileMap;
import com.example.oopmap.Meta.TrueRand;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class TileTest {

    // тест на моки
    @Test
    public void updateTile_TrueRand_TrueRandRandomInvokeAtLeastOnce() {
        try (MockedStatic<TrueRand> trueRandMock = Mockito.mockStatic(TrueRand.class)) {
            //arrange
            trueRandMock.when(TrueRand::nextBoolean).thenReturn(false);
            Tile tile = createDefaultTile();
            tile.setLevel(19);

            //act
            tile.updateTile();

            //assert
            trueRandMock.verify(TrueRand::nextBoolean, Mockito.atLeastOnce());
        }
    }

    // Структурированное базисное тестирование, классы эквивалентности
    @Test
    public void updateTile_LevelLessThan0_ReturnWaterTile() {
        //arrange
        Tile tile = createDefaultTile();
        tile.setLevel(-1);

        //act
        Tile result = tile.updateTile();

        //assert
        Assert.assertEquals(result.getClass(), WaterTile.class);
    }

    // Структурированное базисное тестирование, классы эквивалентности
    @Test
    public void updateTile_LevelGreaterThan0LessThan20AndTrueRandom_ReturnGrassTile() {
        //arrange
        MockedStatic<TrueRand> trueRandMock = Mockito.mockStatic(TrueRand.class);
        trueRandMock.when(TrueRand::nextBoolean).thenReturn(true);
        Tile tile = createDefaultTile();
        tile.setLevel(10);

        //act
        Tile result = tile.updateTile();
        trueRandMock.close();

        //assert
        Assert.assertEquals(result.getClass(), GrassTile.class);
    }

    // Структурированное базисное тестирование, классы эквивалентности
    @Test
    public void updateTile_LevelGreaterThan0LessThan20AndFalseRandom_ReturnForestTile() {
        //arrange
        MockedStatic<TrueRand> trueRandMock = Mockito.mockStatic(TrueRand.class);
        trueRandMock.when(TrueRand::nextBoolean).thenReturn(false);
        Tile tile = createDefaultTile();
        tile.setLevel(10);

        //act
        Tile result = tile.updateTile();
        trueRandMock.close();

        //assert
        Assert.assertEquals(result.getClass(), ForestTile.class);
    }

    // Структурированное базисное тестирование, классы эквивалентности
    @Test
    public void updateTile_LevelGreaterThan20AndBuildedEqual1_ReturnBuildingTile() {
        //arrange
        Tile tile = createDefaultTile();
        tile.setLevel(30);
        tile.makeBuilding(1);

        //act
        Tile result = tile.updateTile();

        //assert
        Assert.assertEquals(result.getClass(), BuildingTile.class);
    }

    // Структурированное базисное тестирование, классы эквивалентности
    @Test
    public void updateTile_LevelGreaterThan20AndBuildedEqual2_ReturnWallTile() {
        //arrange
        Tile tile = createDefaultTile();
        tile.setLevel(30);
        tile.makeBuilding(2);

        //act
        Tile result = tile.updateTile();

        //assert
        Assert.assertEquals(result.getClass(), WallTile.class);
    }

    private Tile createDefaultTile() {
        TileMap tileMap = new TileMap();
        return new Tile(tileMap, 0, 0);
    }
}
