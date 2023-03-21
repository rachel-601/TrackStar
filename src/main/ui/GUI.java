package ui;

import model.Activities;
import model.Run;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private List<Run> run;
    private Activities runs;

    public GUI() {
        initializeGraphics();
    }

    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new GUI();
    }
}
