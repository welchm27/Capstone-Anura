package com.anura.player;

import com.anura.readjsondata.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

public class Player extends Character {
    // Additional attributes specific to the player
    private final String playerName;
    private int exp;
    private Map<String, Integer> playerInventory;
    private EvolutionData currentEvol;
    private int currentHealth;

    // constructors
    public Player(String name) {
        this.playerName = name;
        this.currentEvol = new EvolutionData("tadpole");
        if (currentEvol.getEvolName().equals("tadpole")) {
            setMaxHealth(20);
        }
        this.currentHealth = getMaxHealth();
        this.exp = 0;
        this.playerInventory = new HashMap<>();
        initializePlayer();
    }

    public void addToInventory(String item, int count) {
        playerInventory.put(item, playerInventory.getOrDefault(item, 0) + count);
    }

    public void useItem(String item, int count) {
        if (playerInventory.containsKey(item)) {
            int currentCount = playerInventory.get(item);
            if (currentCount >= count) {
                playerInventory.put(item, currentCount - count);
            } else {
                System.out.println("Not enough " + item + " in inventory!");
            }
        } else {
            System.out.println("Item " + item + " not found in inventory!");
        }
    }

    // Method to prompt the player to enter their name
    public static String promptPlayerName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        return playerName.trim().isEmpty() ? "Freg" : playerName;
    }

    private JsonArray initializePlayer() {
        try {
            Gson gson = new Gson();
            FileReader foodInv = new FileReader("src/main/resources/Food.json");
            return gson.fromJson(foodInv, JsonArray.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to start the game
    public void startGame() {
        // Update the player's name based on the input
        setName(playerName);

        // Now you can use the player object with the entered name throughout the game
        System.out.println("Welcome, " + getName() + "!");
        System.out.println("Your Starting Level is: " + currentEvol.getEvolName());
        // Other game logic

        // Start the game loop
        movePlayer();
    }

    private void movePlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please provide a direction to go >");
        String input = scanner.nextLine();  // Prompt the player for their next move
        String[] move = input.toLowerCase().split(" ", 2);  // Split the input into a command and an argument

        if (move[0].equals("go")) {

        }

        }

    private void go(String direction) {
        // Check if the direction is valid from the current location
        if (rooms.containsKey(currentRoom) && rooms.get(currentRoom).containsKey(direction)) {
            String newRoom = rooms.get(currentRoom).get(direction);
            currentRoom = newRoom; // Move the player to the new room
        } else {
            System.out.println("You can't go that way!");
        }
    }

    // Getter and setter methods for playerName and currentLevel
    public Map<String, Integer> getInventory() {
        return playerInventory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.playerInventory = inventory;
    }
}





