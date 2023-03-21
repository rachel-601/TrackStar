package persistence;

import model.Run;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkRun(Double distance, String date, int duration, Run run) {
        assertEquals(distance, run.getDistance());
        assertEquals(date, run.getDate());
        assertEquals(duration, run.getDuration());
    }
}
