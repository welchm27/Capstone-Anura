package com.anura.player;

import com.anura.GameObject;
import com.anura.main.Helper;
import com.anura.readjsondata.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.fusesource.jansi.Ansi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Player extends Character {
    // Additional attributes specific to the player
    private final String playerName;
    private int exp;
    private Map<String, Integer> playerInventory;
    private EvolutionData currentEvol;
    private int currentHealth;
    private String currentLocation;

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
        this.currentLocation = "pond";
        //initializePlayer();
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
        return playerName.trim().isEmpty() ? "Frog" : playerName;
    }

    public void look(String itemType) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the " + itemType + " you want to look at: ");
        String itemName = scanner.nextLine().trim().toLowerCase();

        GameObject gameObject = new GameObject(itemType,itemName);
        String description = gameObject.getDescription();

        System.out.println("Description: " + description);
    }

//    private JsonArray initializePlayer() {
//        try {
//            Gson gson = new Gson();
////            FileReader foodInv = new FileReader("src/main/resources/Food.json");
//            String foodInv = Helper.readFromResourceFile("Food.json");
//            return gson.fromJson(foodInv, JsonArray.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    // Method to start the game
    public void startGame() {
        // Update the player's name based on the input
        setName(playerName);

        // Now you can use the player object with the entered name throughout the game
        System.out.println("Welcome, " + getName() + "!");
        System.out.println("Your Starting Level is: " + currentEvol.getEvolName());
        // Other game logic

    }

    public String move(String direction, JsonObject mapData) {

        JsonObject currentLocationJson = mapData.get(currentLocation).getAsJsonObject();

        if(currentLocationJson.has(direction)){
            this.currentLocation = currentLocationJson.get(direction).getAsString();
            return currentLocation;
        }else{
            Helper.printColor("\nInvalid action, please try another one.\n" +
                    "Enter to continue..\n", Ansi.Color.RED);
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            return null;
        }
    }

    // Getter and setter methods


    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Map<String, Integer> getInventory() {
        return playerInventory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.playerInventory = inventory;
    }
}





