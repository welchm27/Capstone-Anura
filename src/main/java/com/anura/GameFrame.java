package com.anura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame {
    private final JFrame frame;
    private final JTextArea textArea;
    private final JTextArea statusArea;
    private final JTextField inputField;
    private final JPanel mapPanel;

    public GameFrame() {
        // Create the main JFrame
        frame = new JFrame("Anuna Text Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create the JTextArea for displaying game output
        textArea = new JTextArea();
        textArea.setEditable(false);

        // Create the JTextArea for displaying player status
        statusArea = new JTextArea();
        statusArea.setEditable(false);

        // Create the JTextField for player input
        inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                handlePlayerInput(input);
                inputField.setText(""); // Clear the input field after processing
            }
        });

        // Create a JPanel for the map image (replace "map.png" with your map image file)
        mapPanel = new JPanel();
        ImageIcon mapIcon = new ImageIcon("map.png");
        JLabel mapLabel = new JLabel(mapIcon);
        mapPanel.add(mapLabel);

        // Create a main panel to hold the subpanels
        JPanel mainPanel = new JPanel(new GridLayout(2, 2));
        mainPanel.add(new JScrollPane(textArea));
        mainPanel.add(statusArea);
        mainPanel.add(inputField);
        mainPanel.add(mapPanel);

        // Add the main panel to the JFrame
        frame.add(mainPanel);

        // Set the frame visible
        frame.setVisible(true);

        // Initialize the game and display introduction
        textArea.append("Welcome to Anuna, an epic adventure awaits!\n");
        textArea.append("You are a brave adventurer. Your journey begins...\n");

        // Initialize player status
        statusArea.setText("Name: Player\nLocation: Start\nItems: None");
    }

    // Method to handle player input and update textArea
    private void handlePlayerInput(String input) {
        // Add your game logic here
        // Update textArea with game responses and descriptions

        // Example: Echo the player input as a response
        textArea.append("You: " + input + "\n");

        // Example: Provide a game response
        textArea.append("Game Master: You have chosen the path of destiny.\n");

        // Update statusArea with player status changes
        statusArea.setText("Name: Player\nLocation: Castle\nItems: Key, Potion");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameFrame();
            }
        });
    }
}

