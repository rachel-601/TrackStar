package persistence;

import model.Activities;
import model.Run;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Activities activities = new Activities("My activities");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Activities activities = new Activities("Empty Activities");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyActivities.json");
            writer.open();
            writer.write(activities);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyActivities.json");
            activities = reader.read();
            assertEquals("Empty Activities", activities.getName());
            assertEquals(0, activities.getActivityNumber());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Activities activities = new Activities("General Activities");
            activities.addRun(new Run(4,10,"02/24/2023"));
            activities.addRun(new Run(6.5,30,"02/27/2023"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralActivities.json");
            writer.open();
            writer.write(activities);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralActivities.json");
            activities = reader.read();
            assertEquals("General Activities", activities.getName());
            List<Run> runs = activities.getActivities();
            assertEquals(2, runs.size());
            checkRun(4.0,"02/24/2023",10,runs.get(0));
            checkRun(6.5,"02/27/2023",30,runs.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
