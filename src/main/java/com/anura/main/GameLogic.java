package com.anura.main;

import com.anura.GameMap;
import com.anura.GameObject;
import com.anura.player.Player;
import com.anura.readjsondata.LocationData;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.*;

public class GameLogic {

    public void execute() throws IOException, URISyntaxException {

        Player player = new Player("frog");
        HashSet<String> visitedLocations = new HashSet<>();
        JsonObject mapData;

        Scanner scanner = new Scanner(System.in);

        // Welcome Banner & user instructions
        System.out.println(Ansi.ansi().eraseScreen());
        Helper.printFile("SplashPage.txt", Ansi.Color.GREEN);

        //Background music starts here
        Music.playBGMusic("ShumbaTest.wav");
        Music.setBGMVolume(0.3f);

        // ask for new game or saved game
        System.out.println("Enter " + Ansi.ansi().fgBrightGreen().a("[New]").reset() + " for new game or "
                + Ansi.ansi().fgBlue().a("[Save]").reset() + " to load saved game:");
        String userInput = scanner.nextLine().trim().toLowerCase();

        if (userInput.equals("new")) {

            // Create a new player and prompt for their name
            String playerName = Player.promptPlayerName();
            player.setName(playerName);
            // Start the game
            player.startGame();

            // get Map data
            mapData = GameMap.getMap();

        } else {
            // load saved json stat file
            Gson gson = new Gson();
            JsonObject loadedGameStat = gson.fromJson(Helper.getExternalFile("game-stat.sav"),
                    JsonObject.class);

            mapData = gson.fromJson(loadedGameStat.get("mapData").getAsString(), JsonObject.class);
            visitedLocations = gson.fromJson(loadedGameStat.get("visitedLocations").getAsString(), HashSet.class);
            player.setCurrentLocation(loadedGameStat.get("currentLocation").getAsString());
            player.setName(loadedGameStat.get("playerName").getAsString());

            Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
            player.setInventory(gson.fromJson(loadedGameStat.get("inventory").getAsString(), type));
        }

        while (!userInput.equals("quit")) {

            // update current location
            String currentLocation = player.getCurrentLocation();
            JsonObject updatedLocation = mapData.get(currentLocation).getAsJsonObject();
            boolean enemyPresent = updatedLocation.has("enemy");

            // add current location to visited locations list
            visitedLocations.add(currentLocation);

            // clear screen
            System.out.print(Ansi.ansi().eraseScreen());
            System.out.print(Ansi.ansi().cursor(5, 0));
            // print map of visited locations
            GameMap.printLocations(visitedLocations);
            System.out.println(Ansi.ansi().cursor(28, 0));

//            System.out.println("\nYou are at " + Ansi.ansi().fgBrightGreen().a(currentLocation).reset()
//                    + ". Here is the info of this location:");
//            Helper.printColor(updatedLocation.toString(), Ansi.Color.MAGENTA);

            System.out.println();
            printStatusBoard(player,mapData);
            printDescription(player);

            // move in the map
            System.out.println("\n\nPlease provide a direction to go >");
            userInput = scanner.nextLine().toLowerCase();  // Prompt the player for their next move

            // Split the input into a command and an argument
            String[] moveInput = userInput.toLowerCase().split(" ", 2);

            if (enemyPresent) {
                System.out.println("Any enemy is here!  You must hide or die!");
                if (player.hasItem("leaf")) {  // Checking if the player has a leaf in the inventory
                    System.out.println("You can use a leaf to hide! Enter 'hide' to hide or any other move to continue at your own risk.");

                    userInput = scanner.nextLine().toLowerCase();

                    if (userInput.equals("hide")) {
                        String enemyName = updatedLocation.get("enemy").getAsString();
                        boolean hidSuccessfully = player.hide(enemyName);

                        if (hidSuccessfully) {
                            System.out.println("You hide. It's safe to continue now...");
                            JsonObject locationInMapData = mapData.getAsJsonObject(currentLocation);
                            locationInMapData.remove("enemy");
                            mapData.add(currentLocation, locationInMapData);
                        } else {
                            Helper.printColor("You couldn't hide in time...YOU DIED.", Ansi.Color.RED);
                            break;
                        }
                    } else {
                        Helper.printColor("You chose not to hide...YOU DIED.", Ansi.Color.RED);
                        break;
                    }
                } else {
                    Helper.printColor("There's no place to hide without the leaf...YOU DIED.", Ansi.Color.RED);
                    break;
                }
            } else {
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
                    } else if (userInput.equals("fx")) {
                        handleFXControls(scanner);
                    } else if (userInput.equals("save")) {
                        // Data needed to be saved:
                        // mapData, visitedLocations, Inventory, currentLocation
                        HashMap<String, String> savedData = new HashMap<>();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // pretty print JSON

                        savedData.put("mapData", gson.toJson(mapData));
                        savedData.put("visitedLocations", gson.toJson(visitedLocations));
                        savedData.put("currentLocation", player.getCurrentLocation());
                        savedData.put("playerName", player.getName());
                        savedData.put("inventory", gson.toJson(player.getInventory()));

                        Helper.writeToExternalFile(gson.toJson(savedData), "game-stat.sav");

                    }else {
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
                    if (inputParts.length == 2) {
                        String itemName = inputParts[1];
                        get(player, itemName, mapData);
                    } else {
                        System.out.println("Please provided an item name after 'get'.");
                    }
                } else if (userInput.startsWith("drop")) {
                    String[] inputParts = userInput.split(" ", 2);
                    String itemName = inputParts[1];
                    drop(player, itemName, mapData);
                } else if (userInput.toLowerCase().startsWith("talk")) {
                    String[] inputParts = userInput.split(" ");
                    String npcName = inputParts[1];
                    player.talk(npcName);
                    if("violet".equalsIgnoreCase(npcName)){
                        if(player.hasItem("glass bead")){
                            Helper.printColor("What a beautiful bead!  Is that a gift for me?", Ansi.Color.MAGENTA);
                            System.out.println();
                            Helper.printColor("You give violet the bead, and the two of you run off to start your life together...", Ansi.Color.GREEN);
                            System.out.println();
                            Helper.printColor("CONGRATULATIONS!  YOU WON!", Ansi.Color.GREEN);
                            break;
                        } else {
                            Helper.printColor("Violet seems to want a gift from you to prove that you love her...", Ansi.Color.YELLOW);
                        }
                    }
                    System.out.println("Enter to continue...");
                    scanner.nextLine();
                } else {
                    player.move(moveInput[1].toLowerCase(), mapData);

                    // Sound effect (FX) music starts here
                    Music.playFX("Good.wav"); // Play the sound effect
                }
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
            Music.playBGMusic("ShumbaTest.wav");
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
        System.out.println("What would you like to do with the sound effects? (on/off/volume)\n ");
        String musicCommand = scanner.nextLine();
        if (musicCommand.equalsIgnoreCase("on")) {
            Music.setFXVolume(0.5f); // Turn on sound effects
        } else if(musicCommand.equalsIgnoreCase("off")) {
            Music.setFXVolume(0.0f); // Turn off sound effects (via volume 0.0)
        }
        else if (musicCommand.equalsIgnoreCase("volume")) {
            System.out.println("Enter volume level (low = 0.0 - 1.0 = high):");
            float volume = Float.parseFloat(scanner.nextLine());
            Music.setFXVolume(volume); // Set sound effects volume
        } else {
            System.out.println("Invalid music command.");
        }
    }

    private void printStatusBoard(Player player, JsonObject mapData) {

        // Print the status board with player information
        System.out.println("=== Status Board ===");
        System.out.println("Player: " + player.getName());
        System.out.println("Location: " + player.getCurrentLocation());

        // Print inventory
        System.out.println("Inventory:");
        String italicStyle = "\u001B[3m";
        String resetColor = "\u001B[0m";
        String magenta = "\u001B[35m";
        for (Map.Entry<String, Integer> entry : player.getInventory().entrySet()) {
            String itemName = entry.getKey();
            int itemCount = entry.getValue();
            System.out.printf(" - %-18s: %-2d%n", magenta + itemName + resetColor, itemCount);
        }

        // Print location items
        LocationData locationData = new LocationData(player.getCurrentLocation(), mapData);
        List<String> locationItems = locationData.getLocationItems();
        if(locationItems.isEmpty()) {
            locationItems.add("No Items");
            String item = locationItems.get(0);
            System.out.println("Location Items: " + item);
        }else {
            System.out.println("Location Items: " + locationItems);
        }
        // Add more information as needed

        System.out.println("====================");


    }

    private void printDescription(Player player) {
        String currentLocation = player.getCurrentLocation();
        GameObject locationDesc = new GameObject(currentLocation);
        System.out.println("Location Description: " + locationDesc.getDescription());
    }

}