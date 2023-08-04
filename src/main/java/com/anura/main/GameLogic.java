package com.anura.main;

import com.anura.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class GameLogic {

    public static String filePath = "src/main/resources/SplashPage.txt";
    public static int[] helpLines = {28, 29, 30, 31};

    public void execute() throws IOException {

        Player player;
        String[] stat;

        Scanner scanner = new Scanner(System.in);

        // Welcome Banner & user instructions
        Helper.printSplashPage(filePath);
        Helper.printHelp(filePath, 28, 31);

        // ask for new game or saved game
        System.out.println("Enter [New] for new game or [Save] to load saved game:");
        String userInput = scanner.nextLine().trim().toLowerCase();

        if(userInput.equals("new")){
            stat = new String[]{};

            // Create a new player and prompt for their name
            String playerName = Player.promptPlayerName();
            player = new Player(playerName);

            // Start the game
            player.startGame();

        }else{
            // load saved json stat file

            // TODO: place holder TO BE replaced and deleted
            String playerName = Player.promptPlayerName();
            player = new Player(playerName);
        }

        while(!userInput.equals("quit")){
            // move in the map
            System.out.println("\nPlease provide a direction to go >");
            userInput = scanner.nextLine();  // Prompt the player for their next move

            // Split the input into a command and an argument
            String[] moveInput = userInput.toLowerCase().split(" ", 2);
            if(moveInput.length != 2){
                System.out.println("Invalid input! Please enter one action and one direction(i.e. go south)\n");
                continue;
            }

            player.move(moveInput[1].toLowerCase());
        }

    }

}