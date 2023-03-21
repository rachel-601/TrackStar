package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ActivitiesTest {
    private Activities testActivities;
    private Run run1;
    private Run run2;
    private Run run3;
    private Run run4;
    private Run run5;
    private Run run6;

    @BeforeEach
    void runBefore() {
        testActivities = new Activities("name");
        run1 = new Run(6,30,"01/27/2023");
        run2 = new Run(5,20,"02/01/2023");
        run3 = new Run(10.23,73,"02/03/2023");
        run4 = new Run(6.5,30,"02/10/2023");
        run5 = new Run(5,20,"02/10/2023");
        run6 = new Run(9,20,"02/26/2023");
    }

    @Test
    void testConstructor() {
        assertEquals(0,testActivities.getActivityNumber());
    }

    @Test
    void testTotalCalories() {
        assertEquals(0,testActivities.totalCalories());
        testActivities.addRun(run1);
        testActivities.addRun(run2);
        assertEquals(2,testActivities.getActivityNumber());
        assertEquals(660,testActivities.totalCalories());
        testActivities.addRun(run3);
        assertEquals(3,testActivities.getActivityNumber());
        assertEquals(1273.8,testActivities.totalCalories());
        List<Run> runs = testActivities.getActivities();
        assertEquals(3,runs.size());
    }

    @Test
    void testAvgSpeed() {
        assertEquals(0,testActivities.avgSpeed());
        testActivities.addRun(run1);
        assertEquals(12,testActivities.avgSpeed());
        testActivities.addRun(run2);
        testActivities.addRun(run3);
        assertEquals(11.80,testActivities.avgSpeed());
    }

    @Test
    void testLongestRun() {
        assertEquals(null,testActivities.longestRun());
        testActivities.addRun(run1);
        assertEquals(run1,testActivities.longestRun());
        testActivities.addRun(run2);
        assertEquals(run1,testActivities.longestRun());
        testActivities.addRun(run4);
        assertEquals(run4,testActivities.longestRun());
    }

    @Test
    void testFastestRun() {
        assertEquals(null,testActivities.fastestRun());
        testActivities.addRun(run1);
        assertEquals(run1,testActivities.fastestRun());
        testActivities.addRun(run2);
        assertEquals(run2,testActivities.fastestRun());
        testActivities.addRun(run5);
        assertEquals(run5,testActivities.fastestRun());
        testActivities.addRun(run6);
        assertEquals(run6,testActivities.fastestRun());
        testActivities.addRun(run4);
        assertEquals(run6,testActivities.fastestRun());
    }

    @Test
    void testAddRun() {
        testActivities.addRun(run1);
        assertEquals(1,testActivities.getActivityNumber());
        testActivities.addRun(run3);
        testActivities.addRun(run2);
        assertEquals(3,testActivities.getActivityNumber());
    }
}
