import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
        if(currentEvol.getEvolName() == "tadpole") {
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
    }

    // Getter and setter methods for playerName and currentLevel
    public Map<String, Integer> getInventory() {
        return playerInventory;
    }

    public void setInventory(Map<String, Integer> inventory) {
        this.playerInventory = inventory;
    }

}




