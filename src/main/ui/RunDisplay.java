package ui;

import model.Activities;
import model.Run;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Represents the display area of all runs added
public class RunDisplay extends JPanel {
    private Activities activities;
    private JList runList;
    private DefaultListModel runStringList;

    // EFFECTS: constructs the display area
    public RunDisplay(Activities activities) {
        this.activities = activities;
        setSize(WIDTH / 2, HEIGHT - 25);
        display();
    }

    // MODIFIES: this
    // EFFECTS: creates scroll pane of display with list of runs added
    public void display() {
        makeRunList();
        runList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        runList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        runList.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(runList);
        listScroller.setPreferredSize(new Dimension(250, 250));
        add(listScroller);
    }

    // MODIFIES: this
    // EFFECTS: makes JList of runs in activities
    private void makeRunList() {
        List<Run> runs = activities.getActivities();
        runStringList = new DefaultListModel();
        for (Run r : runs) {
            String run = r.getDate() + ", " + r.getDistance() + " km, " + r.getDuration() + " min";
            runStringList.addElement(run);
        }
        runList = new JList(runStringList);
    }

    // EFFECTS: adds a run into JList
    public void addRun(Run run) {
        String newRun = run.getDate() + ", " + run.getDistance() + " km, " + run.getDuration() + " min";
        runStringList.addElement(newRun);
    }
}
