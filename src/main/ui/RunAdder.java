package ui;

import model.Activities;
import model.Run;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static java.lang.Double.parseDouble;

// Represents the area where user can add a run and displays all the runs.
public class RunAdder extends JPanel {
    private static final String JSON_STORE = "./data/activities.json";
    private Activities activities;

    private JPanel adderPanel;
    private JLabel dateLabel;
    private JLabel distanceLabel;
    private JLabel durationLabel;
    private JLabel addedLabel;
    private JTextField runDate;
    private JTextField runDistance;
    private JTextField runDuration;
    private JButton addButton;
    private ClickHandler handler;

    private RunDisplay runDisplay;
    private Box savePanel;
    private JButton save;
    private JButton load;
    private ClickHandler2 handler2;
    private ClickHandler3 handler3;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Constructs the user input panel, display panel, and save and load buttons
    public RunAdder(Activities activities) {
        this.activities = activities;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setSize(WIDTH,HEIGHT);
        createAdderPanel();
        createDisplayPanel();
        createSavePanel();
        add(savePanel, BorderLayout.NORTH);
        add(runDisplay,BorderLayout.WEST);
        add(adderPanel,BorderLayout.EAST);
    }

    // MODIFIES: this, adderPanel, handler, addButton
    // EFFECTS: creates panel that takes user inputs
    public void createAdderPanel() {
        adderPanel = new JPanel();
        setSize(WIDTH / 2,HEIGHT - 25);
        adderPanel.setLayout(new GridLayout(8,1));
        createUserInputs();
        adderPanel.add(dateLabel);
        adderPanel.add(runDate);
        adderPanel.add(distanceLabel);
        adderPanel.add(runDistance);
        adderPanel.add(durationLabel);
        adderPanel.add(runDuration);
        adderPanel.add(addedLabel);
        addButton = new JButton("Add Run");
        addButton.setBounds(10,10,80,25);
        handler = new ClickHandler();
        addButton.addActionListener(handler);
        adderPanel.add(addButton);
    }

    // EFFECTS: creates input boxes for date, distance, and duration of run
    public void createUserInputs() {
        createLabels();
        createTextFields();
    }

    // MODIFIES: this, date/distance/duration/added labels
    // EFFECTS: creates labels for input boxes
    public void createLabels() {
        dateLabel = new JLabel("Date (MM/DD/YYYY):");
        dateLabel.setBounds(10,10,80,25);
        distanceLabel = new JLabel("Distance (km, to 2 decimals):");
        distanceLabel.setBounds(10,60,80,25);
        durationLabel = new JLabel("Duration (minutes):");
        durationLabel.setBounds(10,110,80,25);
        addedLabel = new JLabel("");
        addedLabel.setBounds(10,170,80,25);
    }

    // MODIFIES: this, date/distance/duration text fields
    // EFFECTS: creates text fields for input boxes
    public void createTextFields() {
        runDate = new JTextField(20);
        runDate.setBounds(10,40,165,25);
        runDistance = new JTextField(20);
        runDistance.setBounds(40,90,165,25);
        runDuration = new JTextField(20);
        runDuration.setBounds(10,140,165,25);
    }

    // MODIFIES: this, runDisplay
    // EFFECTS: creates the area that displays all runs
    public void createDisplayPanel() {
        runDisplay = new RunDisplay(activities);
    }

    // MODIFIES: this, save, load, savePanel, handlers
    // EFFECTS: creates save and load buttons
    public void createSavePanel() {
        // create save button
        save = new JButton("Save");
        save.setBounds(10,190,80,25);
        handler2 = new ClickHandler2();
        save.addActionListener(handler2);
        // create load button
        load = new JButton("Load");
        load.setBounds(10,190,80,25);
        handler3 = new ClickHandler3();
        load.addActionListener(handler3);
        // create box for buttons
        savePanel = Box.createHorizontalBox();
        savePanel.add(Box.createHorizontalGlue());
        savePanel.add(save);
        savePanel.add(load);
        savePanel.add(Box.createHorizontalGlue());
    }

    // A listener for key events for add run button
    private class ClickHandler implements ActionListener {

        // EFFECTS: adds new run using user's input
        @Override
        public void actionPerformed(ActionEvent e) {
            String date = runDate.getText();
            double distance = parseDouble(runDistance.getText());
            int duration = Integer.parseInt(runDuration.getText());
            Run run = new Run(distance, duration, date);
            activities.addRun(run);
            runDisplay.addRun(run);
            addedLabel.setText("Run added!");
        }
    }

    // A listener for key events for save button
    private class ClickHandler2 implements ActionListener {

        // EFFECTS: saves the activities to file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(activities);
                jsonWriter.close();
                System.out.println("Saved " + activities.getName() + " to " + JSON_STORE);
            } catch (FileNotFoundException i) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // A listener for key events for load button
    private class ClickHandler3 implements ActionListener {

        // EFFECTS: loads the activities from file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Activities loadedActivities = jsonReader.read();
                List<Run> runs = loadedActivities.getActivities();
                System.out.println("Loaded " + activities.getName() + " from " + JSON_STORE);
                for (Run r : runs) {
                    activities.addRun(r);
                }
                for (Run r : runs) {
                    runDisplay.addRun(r);
                }
            } catch (IOException i) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }
}
