package com.anura;

import com.anura.controller.BridgeOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

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

        // Create a JPanel for map (need to get this to work)
        mapPanel = new JPanel();
        ImageIcon mapIcon = new ImageIcon("resources/AnuraGameVisual3.png");
        JLabel mapLabel = new JLabel(mapIcon);
        mapPanel.add(mapLabel);

        // Create a "Game Start" button
        JButton startButton = new JButton("Game Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the text area and reset player status when the game starts
                textArea.setText("");
                statusArea.setText("Name: Player\nLocation: Start\nItems: None");
            }
        });

        // Create a control panel to hold the start button
        JPanel controlPanel = new JPanel();
        controlPanel.add(startButton);

        // Create a main panel to hold the subpanels and the control panel
        JPanel mainPanel = new JPanel(new GridLayout(2, 2));
        mainPanel.add(new JScrollPane(textArea));
        mainPanel.add(statusArea);
        mainPanel.add(inputField);
        mainPanel.add(mapPanel);

        // Create a container panel to hold the main panel and the control panel
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(mainPanel, BorderLayout.CENTER);
        containerPanel.add(controlPanel, BorderLayout.SOUTH);

        // Add the container panel to the JFrame
        frame.add(containerPanel);

        // Set the frame visible
        frame.setVisible(true);

        // Initialize the game and display introduction
        textArea.append("Welcome to Anuna, an epic adventure awaits!\n");
        textArea.append("You are a brave adventurer. Your journey begins...\n");
    }

    // Method to handle player input and update textArea
    private void handlePlayerInput(String input) {
        // Update textArea with game responses and descriptions

        // EXXON *****
        // nicks handle input and append to textarea code
        // this should replace the calls to text area below by passing parameters to BridgeOutput class
        System.setOut(new PrintStream(new BridgeOutput(), true));

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

