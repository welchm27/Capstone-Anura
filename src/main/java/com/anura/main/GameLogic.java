package com.anura.main;

import com.anura.GameMap;
import com.anura.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.fusesource.jansi.Ansi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class GameLogic {

    public static int[] helpLines = {28, 29, 30, 31};

    public void execute() throws IOException, URISyntaxException {

        Player player;
        String[] stat;

        Scanner scanner = new Scanner(System.in);

        // Welcome Banner & user instructions
//        Helper.printSplashPage("SplashPage.txt");
//        Helper.printHelp("SplashPage.txt", 28, 31);
        Helper.printFile("SplashPage.txt", Ansi.Color.GREEN);

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

            String currentLocation = player.getCurrentLocation();
            JsonObject updatedLocation = mapData.get(currentLocation).getAsJsonObject();

            // clear screen
            System.out.print(Ansi.ansi().eraseScreen());
            System.out.print(Ansi.ansi().cursor(5, 0));
            Helper.printFile("VisualMap.txt", Ansi.Color.GREEN);

            System.out.println("\nYou are at " + Ansi.ansi().fgBrightGreen().a(currentLocation).reset()
                    + ". Here is the info of this location:");
            Helper.printColor(updatedLocation.toString(), Ansi.Color.MAGENTA);

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
                } else {
                    Helper.printColor("\nInvalid input! Please enter one action and one direction(i.e. go south).\n",
                            Ansi.Color.RED);
                }
                System.out.println("Enter to continue..");
                scanner.nextLine();
            }else{
                if (userInput.toLowerCase().startsWith("look")) {
                    String[] inputParts = userInput.split(" ");
                    String itemType = inputParts[1];
                    player.look(itemType);
                    System.out.println("Enter to continue..");
                    scanner.nextLine();
                } else{
                    player.move(moveInput[1].toLowerCase(), mapData);
                }
            }
        }

        Helper.printColor("\nBye!", Ansi.Color.MAGENTA);
    }
}