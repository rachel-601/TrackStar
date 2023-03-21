package persistence;

import model.Activities;
import model.Run;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Activities activities = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyActivities() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyActivities.json");
        try {
            Activities activities = reader.read();
            assertEquals("Empty Activities", activities.getName());
            assertEquals(0, activities.getActivityNumber());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralActivities() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralActivities.json");
        try {
            Activities activities = reader.read();
            assertEquals("General Activities", activities.getName());
            List<Run> runs = activities.getActivities();
            assertEquals(2, activities.getActivityNumber());
            checkRun(3.0,"02/14/2023", 10, runs.get(0));
            checkRun(4.0, "02/15/2023", 15, runs.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
