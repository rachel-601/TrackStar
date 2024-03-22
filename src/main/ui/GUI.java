package ui;

import model.Activities;
import model.Event;
import model.EventLog;
import model.Run;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// GUI for Fitness Tracker
public class GUI extends JFrame implements LogPrinter {

    public static final int WIDTH = 2000;
    public static final int HEIGHT = 1050;

    private RunAdder panel;
    private Activities activities;
    private JLabel fastest;
    private JButton button;
    private ClickHandler handler;
    private Box hbox;

    // EFFECTS: constructs the GUI
    public GUI() {
        this.activities = new Activities("Your runs");
        setPanel();
        setFastestPanel();
        setFrame();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
            }
        });
    }

    // MODIFIES: this, panel
    // EFFECTS: sets up panel that takes in user inputs and displays all runs
    private void setPanel() {
        panel = new RunAdder(this.activities);
    }

    // MODIFIES: this, f
    // EFFECTS: sets up frame of GUI
    private void setFrame() {
        setSize(WIDTH,HEIGHT);
        add(panel,BorderLayout.NORTH);
        add(hbox,BorderLayout.SOUTH);
        add(new JLabel(new ImageIcon("/Users/rachelshi/Downloads/Fitness.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Fitness Tracker");
        pack();
        setVisible(true);
    }

    // MODIFIES: this, fastest, button, handler, hbox
    // EFFECTS: sets up panel that displays the fastest run
    private void setFastestPanel() {
        fastest = new JLabel("");
        button = new JButton("Show Fastest");
        button.setBounds(10,10,80,25);
        handler = new ClickHandler();
        button.addActionListener(handler);

        hbox = Box.createHorizontalBox();
        hbox.add(Box.createHorizontalGlue());
        hbox.add(button);
        hbox.add(fastest);
        hbox.add(Box.createHorizontalGlue());
    }

    public static void main(String[] args) {
        new GUI();
    }

    @Override
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n\n");
        }
    }

    // A listener for key events for show fastest run button
    private class ClickHandler implements ActionListener {

        // EFFECTS: displays the fastest run recorded, or gives message if no runs
        @Override
        public void actionPerformed(ActionEvent e) {
            Run fastestRun = activities.fastestRun();
            if (fastestRun == null) {
                fastest.setText("No runs inputted!");
                fastest.repaint();
            } else {
                String date = fastestRun.getDate();
                String distance = Double.toString(fastestRun.getDistance());
                String duration = Integer.toString(fastestRun.getDuration());
                String display = date + ", " + distance + " km, " + duration + " min";
                fastest.setText(display);
                fastest.repaint();
            }
        }
    }
}
