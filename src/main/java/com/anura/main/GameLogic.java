package com.anura.main;

import com.anura.GameMap;
import com.anura.player.Player;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class GameLogic {

    public static int[] helpLines = {28, 29, 30, 31};

    public void execute() throws IOException, URISyntaxException {

        Player player;
        String[] stat;
        HashSet<String> visitedLocations = new HashSet<>();

        Scanner scanner = new Scanner(System.in);

        // Welcome Banner & user instructions
//        Helper.printSplashPage("SplashPage.txt");
//        Helper.printHelp("SplashPage.txt", 28, 31);
        Helper.printFile("SplashPage.txt", Ansi.Color.GREEN);

        //Background music starts here
//        Music.playBGMusic("random String");
//        Music.setBGMVolume(0.3f);

        // ask for new game or saved game
        System.out.println("Enter " + Ansi.ansi().fgBrightGreen().a("[New]").reset() + " for new game or "
                + Ansi.ansi().fgBlue().a("[Save]").reset() + " to load saved game:");
        String userInput = scanner.nextLine().trim().toLowerCase();

        if (userInput.equals("new")) {
            stat = new String[]{};

            // Create a new player and prompt for their name
            String playerName = Player.promptPlayerName();
            player = new Player(playerName);

            // Start the game
            player.startGame();

        } else {
            // load saved json stat file

            // TODO: place holder TO BE replaced and deleted
            String playerName = Player.promptPlayerName();
            player = new Player(playerName);
        }

        // get Map data
        JsonObject mapData = GameMap.getMap();

        while (!userInput.equals("quit")) {

            // update current location
            String currentLocation = player.getCurrentLocation();
            JsonObject updatedLocation = mapData.get(currentLocation).getAsJsonObject();

            // add current location to visited locations list
            visitedLocations.add(currentLocation);

            // clear screen
            System.out.print(Ansi.ansi().eraseScreen());
            System.out.print(Ansi.ansi().cursor(5, 0));
            // print map of visited locations
            GameMap.printLocations(visitedLocations);
            System.out.println(Ansi.ansi().cursor(28, 0));

            System.out.println("\nYou are at " + Ansi.ansi().fgBrightGreen().a(currentLocation).reset()
                    + ". Here is the info of this location:");
            Helper.printColor(updatedLocation.toString(), Ansi.Color.MAGENTA);

            player.displayInventory();

            // move in the map
            System.out.println("\n\nPlease provide a direction to go >");
            userInput = scanner.nextLine().toLowerCase();  // Prompt the player for their next move

            // Split the input into a command and an argument
            String[] moveInput = userInput.toLowerCase().split(" ", 2);

            if (moveInput.length != 2) {
                if (userInput.equals("quit")) {
                    break;
                } else if (userInput.equals("help")) {
                    Helper.printFile("Help.txt", Ansi.Color.GREEN);
                } else if (userInput.equals("map")) {
                    Helper.printFile("VisualMap.txt", Ansi.Color.GREEN);
                } else if (userInput.equals("inventory")) {
                    player.displayInventory();
                } else if (userInput.equals("music")) {
                    handleMusicControls(scanner);
                }else if (userInput.equals("fx")) {
                    handleFXControls(scanner);
                } else {
                    Helper.printColor("\nInvalid input! Please enter one action and one direction(i.e. go south).\n",
                            Ansi.Color.RED);
                }
                System.out.println("Enter to continue..");
                scanner.nextLine();
            } else if (userInput.toLowerCase().startsWith("look")) {
                String[] inputParts = userInput.split(" ");
                String itemType = inputParts[1];
                player.look(itemType);
                System.out.println("Enter to continue..");
                scanner.nextLine();
            } else if (userInput.startsWith("get")) {
                String[] inputParts = userInput.split(" ", 2);
                if(inputParts.length == 2) {
                    String itemName = inputParts[1];
                    get(player, itemName, mapData);
                }else {
                    System.out.println("Please provided an item name after 'get'.");
                }
            } else if(userInput.startsWith("drop")) {
                String[] inputParts = userInput.split(" ", 2);
                String itemName = inputParts[1];
                drop(player, itemName, mapData);
            }  else if(userInput.toLowerCase().startsWith("talk")) {
                String[] inputParts = userInput.split(" ");
                String npcName = inputParts[1];
                player.talk(npcName);
                System.out.println("Enter to continue...");
                scanner.nextLine();
            }else {
                player.move(moveInput[1].toLowerCase(), mapData);
                //Sound effect (FX) music starts here
//                Music.playFX("/src/main/resources/Good.wav"); // Play the sound effect
//                try {
//                    Thread.sleep(990); // Wait for the sound effect to finish playing
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Music.playBGMusic("/src/main/resources/ShumbaTest.wav"); // Resume background music
//                Music.setBGMVolume(0.3f); // Set background music volume
            }
        }

        Helper.printColor("\nBye!", Ansi.Color.MAGENTA);
    }

    public void get(Player player, String itemName, JsonObject mapData) {
        itemName = itemName.toLowerCase();
        JsonObject locationData = mapData.get(player.getCurrentLocation()).getAsJsonObject();

        if (locationData.has("item")) {
            JsonArray itemsInLocation = locationData.getAsJsonArray("item");
            JsonArray updatedItems = new JsonArray();
            boolean itemFound = false;

            for (JsonElement itemElement : itemsInLocation) {
                String itemNameInLocation = itemElement.getAsString();

                if (!itemName.equals(itemNameInLocation)) {
                    updatedItems.add(itemElement);
                } else {
                    player.addToInventory(itemName, 1);
                    System.out.println("You picked up " + itemName + ".");
                    itemFound = true;
                }
            }

            if (itemFound) {
                locationData.add("item", updatedItems);
            } else {
                System.out.println("Cannot find " + itemName + " here.");
            }
        } else {
            System.out.println("There are no items to pick up here.");
        }
    }

    public void drop(Player player, String itemName, JsonObject mapData) {
        itemName = itemName.toLowerCase();
        JsonObject locationData = mapData.get(player.getCurrentLocation()).getAsJsonObject();
        JsonArray newItems = locationData.getAsJsonArray("item");
        if (newItems == null) {
            newItems = new JsonArray();
            locationData.add("item", newItems); // Set the new JsonArray in the locationData
        }
        boolean itemFound = false;
        Map<String, Integer> inventory = player.getInventory();
        if (!inventory.containsKey(itemName)) {
            System.out.println("You do not have this item in inventory");
        }else {
            newItems.add(itemName);
            inventory.remove(itemName);
            itemFound = true;
        }
        if(itemFound){
            locationData.add("item", newItems);
        }
    }

    private void handleMusicControls(Scanner scanner) {
        System.out.println("What would you like to do with the background music? (start/stop/volume)\n ");
        String musicCommand = scanner.nextLine();

        if (musicCommand.equalsIgnoreCase("start")) {
            Music.playBGMusic("/src/main/resources/ShumbaTest.wav");
        } else if (musicCommand.equalsIgnoreCase("stop")) {
            Music.stopBackgroundMusic();
        } else if (musicCommand.equalsIgnoreCase("volume")) {
            System.out.println("Enter volume level (low = 0.0 - 1.0 = high):");
            float volume = Float.parseFloat(scanner.nextLine());
            Music.setBGMVolume(volume);
        } else {
            System.out.println("Invalid music command.");
        }
    }

    private void handleFXControls(Scanner scanner) {
        System.out.println("What would you like to do with the sound effects? (off/volume)\n ");
        String musicCommand = scanner.nextLine();
        if (musicCommand.equalsIgnoreCase("off")) {
            Music.stopFX(); // Stop sound effects
        } else if (musicCommand.equalsIgnoreCase("volume")) {
            System.out.println("Enter volume level (low = 0.0 - 1.0 = high):");
            float volume = Float.parseFloat(scanner.nextLine());
            Music.setFXVolume(volume); // Set sound effects volume
        } else {
            System.out.println("Invalid music command.");
        }
    }
}