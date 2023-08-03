package com.anura.main;

import com.anura.player.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class GameLogic {

    public static String filePath = "src/main/resources/SplashPage.txt";
    public static int[] helpLines = {28, 29, 30, 31};

    public void execute() throws IOException {

        String[] stat;

        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        // Game Running Logic
        while(!userInput.equals("Quit")){

            // Welcome Banner
            printSplashPage(filePath);
            // User Instructions
            printHelp(filePath, 28, 31);

            // ask for new game or saved game
            System.out.println("Enter [New] for new game or [Save] to load saved game:");
            userInput = scanner.nextLine();

            if(userInput.equals("New")){
                stat = new String[]{};

                // Create a new player and prompt for their name
                String playerName = Player.promptPlayerName();
                Player player = new Player(playerName);
                // Start the game
                player.startGame();
                System.out.println();
            }
        }


    }
    public static void printSplashPage(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null){
                System.out.println(line);
            }
        }
    }

    public static void printHelp (String filePath, int startLine, int endLine ) throws IOException {
        try (BufferedReader reader =  new BufferedReader(new FileReader(filePath))){
            String line;
            int lineNumber = 0;

            while((line = reader.readLine()) != null) {
                if (lineNumber >= startLine && lineNumber <=endLine) {
                    System.out.println(line);
                }
                lineNumber++;
                if(lineNumber > endLine){
                    break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}