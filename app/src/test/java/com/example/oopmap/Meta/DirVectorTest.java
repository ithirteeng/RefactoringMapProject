package com.example.oopmap.Meta;

import org.junit.Assert;
import org.junit.Test;

public class DirVectorTest {

    // Структурированное базисное тестирование
    @Test
    public void Constructor_DirectionMod8Equals1_XEqualsMinus1AndYEquals1() {
        //arrange
        int direction = 1;

        //act
        DirVector vector = new DirVector(direction);

        //assert
        Assert.assertEquals(-1, vector.x);
        Assert.assertEquals(1, vector.y);
    }

    // Структурированное базисное тестирование
    @Test
    public void Constructor_DirectionMod8Equals2_XEquals0AndYEquals1() {
        //arrange
        int direction = 2;

        //act
        DirVector vector = new DirVector(direction);

        //assert
        Assert.assertEquals(0, vector.x);
        Assert.assertEquals(1, vector.y);
    }

    // Структурированное базисное тестирование
    @Test
    public void Constructor_DirectionMod8Equals3_XEquals1AndYEquals1() {
        //arrange
        int direction = 3;

        //act
        DirVector vector = new DirVector(direction);

        //assert
        Assert.assertEquals(1, vector.x);
        Assert.assertEquals(1, vector.y);
    }

    // Структурированное базисное тестирование
    @Test
    public void Constructor_DirectionMod8Equals4_XEquals1AndYEquals0() {
        //arrange
        int direction = 4;

        //act
        DirVector vector = new DirVector(direction);

        //assert
        Assert.assertEquals(1, vector.x);
        Assert.assertEquals(0, vector.y);
    }

    // Структурированное базисное тестирование
    @Test
    public void Constructor_DirectionMod8Equals5_XEquals1AndYEqualsMinus1() {
        //arrange
        int direction = 5;

        //act
        DirVector vector = new DirVector(direction);

        //assert
        Assert.assertEquals(1, vector.x);
        Assert.assertEquals(-1, vector.y);
    }

    // Структурированное базисное тестирование
    @Test
    public void Constructor_DirectionMod8Equals6_XEquals0AndYEqualsMinus1() {
        //arrange
        int direction = 6;

        //act
        DirVector vector = new DirVector(direction);

        //assert
        Assert.assertEquals(0, vector.x);
        Assert.assertEquals(-1, vector.y);
    }

    // Структурированное базисное тестирование
    @Test
    public void Constructor_DirectionMod8Equals7_XEqualsMinus1AndYEqualsMinus1() {
        //arrange
        int direction = 7;

        //act
        DirVector vector = new DirVector(direction);

        //assert
        Assert.assertEquals(-1, vector.x);
        Assert.assertEquals(-1, vector.y);
    }

    // Структурированное базисное тестирование, граничные значения, классы эквивалентности
    @Test
    public void Constructor_DirectionMod8Equals0_XEqualsMinus1AndYEquals0() {
        //arrange
        int direction = 8;

        //act
        DirVector vector = new DirVector(direction);

        //assert
        Assert.assertEquals(-1, vector.x);
        Assert.assertEquals(0, vector.y);
    }

    // Структурированное базисное тестирование, граничные значения, классы эквивалентности
    @Test
    public void Constructor_NegativeDirection_XEqualsMinus1AndYEquals0() {
        //arrange
        int direction = -1213;

        //act
        DirVector vector = new DirVector(direction);

        //assert
        Assert.assertEquals(-1, vector.x);
        Assert.assertEquals(0, vector.y);
    }

    // Структурированное базисное тестирование, граничные значения, классы эквивалентности
    @Test
    public void Constructor_BigDirectionNumberMod8Equals0_XEqualsMinus1AndYEquals0() {
        //arrange
        int direction = 3452448;

        //act
        DirVector vector = new DirVector(direction);

        //assert
        Assert.assertEquals(-1, vector.x);
        Assert.assertEquals(0, vector.y);
    }
}
