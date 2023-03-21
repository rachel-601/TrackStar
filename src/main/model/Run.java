package model;

import org.json.JSONObject;
import persistence.Writable;

import java.text.DecimalFormat;

// Represents a run that has a distance in km, duration in minutes, and the date it occurred on
public class Run implements Writable {
    private double distance;
    private int duration;
    private String date;
    private DecimalFormat df = new DecimalFormat("#.##");

    // REQUIRES: distance > 0 and up to 2 decimal places,
    //           duration > 0 ,
    //           date is in "MM/DD/YYYY" format
    // EFFECTS: creates a new run
    public Run(double distance, int duration, String date) {
        this.date = date;
        this.distance = distance;
        this.duration = duration;
    }

    // MODIFIES: this
    // EFFECTS: calculates speed of run based on distance and duration in km/h to 2 decimal places
    public double calculateSpeed() {
        return Double.parseDouble(df.format(distance / duration * 60));
    }

    // MODIFIES: this
    // EFFECTS: calculates calories burned based on run (average person burns 60 calories per km)
    public double calculateCalories() {
        return Double.parseDouble(df.format(60 * distance));
    }

    // getters
    public double getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public String getDate() {
        return date;
    }

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date",date);
        json.put("distance",distance);
        json.put("duration",duration);
        return json;
    }
}
