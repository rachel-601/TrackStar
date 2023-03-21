package model;

// Represents a list of runs that user has inputted into the Fitness Tracker
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

// Represents list of workouts done by user
public class Activities implements Writable {
    private List<Run> activities;
    private DecimalFormat df = new DecimalFormat("#.##");
    private String name;

    // EFFECTS: constructor for list of workouts done
    public Activities(String name) {
        activities = new ArrayList<>();
        this.name = name;
    }

    // EFFECTS: calculates total calories burned from all workouts in Activities
    public double totalCalories() {
        double calories = 0;
        for (Run r : activities) {
            calories += r.calculateCalories();
        }
        return calories;
    }

    // EFFECTS: calculates average speed from all workouts in Activities
    public double avgSpeed() {
        double speed = 0;
        for (Run r : activities) {
            speed += r.calculateSpeed();
        }
        if (speed == 0) {
            return 0.0;
        }
        return Double.parseDouble(df.format(speed / activities.size()));
    }

    // EFFECTS: returns the longest run with workout details,
    //          if multiple runs tied for longest, return most recently added to list,
    //          return null if no runs in activities
    public Run longestRun() {
        if (activities.size() == 0) {
            return null;
        }
        Run longest = activities.get(0);
        for (Run r : activities) {
            if (r.getDuration() >= longest.getDuration()) {
                longest = r;
            }
        }
        return longest;
    }

    // EFFECTS: returns the fastest run with workout details,
    //          if multiple runs tied for fastest, return most recently added to list,
    //          return null if no runs in activities
    public Run fastestRun() {
        if (activities.size() == 0) {
            return null;
        }
        Run fastest = activities.get(0);
        for (Run r : activities) {
            if (r.calculateSpeed() >= fastest.calculateSpeed()) {
                fastest = r;
            }
        }
        return fastest;
    }

    // MODIFIES: this
    // EFFECTS: adds a run to Activities
    public void addRun(Run run) {
        this.activities.add(run);
    }

    // getters
    public int getActivityNumber() {
        return activities.size();
    }

    public String getName() {
        return name;
    }

    public List<Run> getActivities() {
        return activities;
    }


    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("activities", activitiesToJson());
        return json;
    }

    // EFFECTS: returns runs in this Activities list as a JSON array
    private JSONArray activitiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Run r : activities) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }
}
