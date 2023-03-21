package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunTest {
    private Run testRun;
    private Run testRun2;
    private Run testRun3;
    private Run testRun4;

    @BeforeEach
    void runBefore() {
        testRun = new Run(6,30,"02/22/2023");
        testRun2 = new Run(5.3,30,"02/23/2023");
        testRun3 = new Run(10,73,"02/03/2023");
        testRun4 = new Run(6.32,54,"02/03/2023");
    }

    @Test
    void testConstructor() {
        assertEquals(6,testRun.getDistance());
        assertEquals(30,testRun.getDuration());
        assertEquals("02/22/2023",testRun.getDate());
    }

    @Test
    void testCalculateSpeed() {
        assertEquals(12,testRun.calculateSpeed());
    }

    @Test
    void testCalculateSpeedDecimals() {
        assertEquals(10.6,testRun2.calculateSpeed());
    }

    @Test
    void testCalculateSpeedLongDecimals() {
        assertEquals(8.22,testRun3.calculateSpeed());
        assertEquals(7.02,testRun4.calculateSpeed());
    }

    @Test
    void testCalculateCalories() {
        assertEquals(360,testRun.calculateCalories());
        assertEquals(379.2,testRun4.calculateCalories());
    }

}