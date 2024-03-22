package ui;

import model.Activities;
import model.Run;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;
import java.text.DecimalFormat;

// Fitness Tracker application
public class TrackerApp {
    private static final String JSON_STORE = "./data/activities.json";
    private Scanner input;
    private Activities runs;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private DecimalFormat df = new DecimalFormat("#.##");

    // EFFECTS: runs the tracker application
    public TrackerApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        runs = new Activities("Your activities");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nHave a good run!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("n")) {
            addNewRun();
        } else if (command.equals("s")) {
            showAvgSpeed();
        } else if (command.equals("c")) {
            showTotalCalories();
        } else if (command.equals("f")) {
            showFastestRun();
        } else if (command.equals("l")) {
            showLongestRun();
        } else if (command.equals("save")) {
            saveActivities();
        } else if (command.equals("load")) {
            loadActivities();
        } else {
            System.out.println("Please select valid option");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes activities
    private void init() {
        runs = new Activities("Your runs");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    private void displayMenu() {
        System.out.println("\nWelcome to Fitness Tracker! Please select what you would like to do:");
        System.out.println("\tn -> add a new run");
        System.out.println("\ts -> calculate average speed of all runs");
        System.out.println("\tc -> calculate total calories burned from all runs");
        System.out.println("\tf -> show fastest run");
        System.out.println("\tl -> show longest run");
        System.out.println("\tsave -> save activities to file");
        System.out.println("\tload -> load activities from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds a new run to runs
    private void addNewRun() {
        System.out.println("Please enter the details of your run:");
        System.out.println("Date of the run (format: MM/DD/YYYY)?");
        String date = input.next();
        while (Integer.parseInt(date.substring(0,2)) > 12 || Integer.parseInt(date.substring(0,2)) < 1
                || Integer.parseInt(date.substring(3,5)) > 31 || Integer.parseInt(date.substring(6)) > 2023) {
            System.out.println("Date is invalid, please re-enter date:");
            date = input.next();
        }
        System.out.println("Distance of your run (in kilometers up to 2 decimals)?");
        double distance = Double.parseDouble(df.format(Double.parseDouble(input.next())));
        System.out.println("Duration of your run (in minutes)?");
        int duration = Integer.parseInt(input.next());
        Run run = new Run(distance,duration,date);
        runs.addRun(run);
        System.out.println("Run has been added!");
    }

    // MODIFIES: this
    // EFFECTS: displays average speed of all runs
    private void showAvgSpeed() {
        double avgSpeed = runs.avgSpeed();
        System.out.println("Your average speed is: " + avgSpeed + " km/h");
    }

    // MODIFIES: this
    // EFFECTS: displays total calories burned from all runs
    private void showTotalCalories() {
        double calories = runs.totalCalories();
        System.out.println("Total calories burned: " + calories);
    }

    // MODIFIES: this
    // EFFECTS: displays the fastest run out all runs,
    //          shows all details (date, distance, duration, speed, calories burned)
    private void showFastestRun() {
        Run fastestRun = runs.fastestRun();
        if (fastestRun == null) {
            System.out.println("You have no runs recorded! Please add a new run first");
        } else {
            System.out.println("\nYour fastest run:");
            System.out.println("\tDate: " + fastestRun.getDate());
            System.out.println("\tDistance: " + fastestRun.getDistance() + " km");
            System.out.println("\tDuration: " + fastestRun.getDuration() + " minutes");
            System.out.println("\tSpeed: " + fastestRun.calculateSpeed() + " km/h");
            System.out.println("\tCalories burned: " + fastestRun.calculateCalories());
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the longest run out all runs,
    //          shows all details (date, distance, duration, speed, calories burned)
    private void showLongestRun() {
        Run longestRun = runs.longestRun();
        if (longestRun == null) {
            System.out.println("You have no runs recorded! Please add a new run first");
        } else {
            System.out.println("\nYour longest run:");
            System.out.println("\tDate: " + longestRun.getDate());
            System.out.println("\tDistance: " + longestRun.getDistance() + " km");
            System.out.println("\tDuration: " + longestRun.getDuration() + " minutes");
            System.out.println("\tSpeed: " + longestRun.calculateSpeed() + " km/h");
            System.out.println("\tCalories burned: " + longestRun.calculateCalories());
        }
    }

    // EFFECTS: saves the activities to file
    private void saveActivities() {
        try {
            jsonWriter.open();
            jsonWriter.write(runs);
            jsonWriter.close();
            System.out.println("Saved " + runs.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads activities from file
    private void loadActivities() {
        try {
            runs = jsonReader.read();
            System.out.println("Loaded " + runs.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
