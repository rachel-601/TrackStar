package persistence;

import model.Run;
import model.Activities;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads activities from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads activities from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Activities read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseActivities(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses activities from JSON object and returns it
    private Activities parseActivities(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Activities activities = new Activities(name);
        addActivities(activities, jsonObject);
        return activities;
    }

    // MODIFIES: activities
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addActivities(Activities activities, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("activities");
        for (Object json : jsonArray) {
            JSONObject nextRun = (JSONObject) json;
            addRun(activities, nextRun);
        }
    }

    // MODIFIES: activities
    // EFFECTS: parses run from JSON object and adds it to activities
    private void addRun(Activities activities, JSONObject jsonObject) {
        int duration = jsonObject.getInt("duration");
        String date = jsonObject.getString("date");
        Double distance = jsonObject.getDouble("distance");
        Run run = new Run(distance, duration, date);
        activities.addRun(run);
    }

}
