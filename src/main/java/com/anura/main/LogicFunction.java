package com.anura.main;

import com.anura.player.Player;
import com.google.gson.JsonObject;
import org.fusesource.jansi.Ansi;

import java.util.Scanner;

public class LogicFunction {
    public static boolean enemyShow(Player player, String userInput, Scanner scanner, JsonObject updatedLocation, JsonObject mapData, String currentLocation){
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
                        return hidSuccessfully;
                    } else {
                        Helper.printColor("You couldn't hide in time...YOU DIED.", Ansi.Color.RED);
                        return false;
                    }
                } else {
                    Helper.printColor("You chose not to hide...YOU DIED.", Ansi.Color.RED);
                    return false;
                }
            } else {
                Helper.printColor("There's no place to hide without the leaf...YOU DIED.", Ansi.Color.RED);
                return false;
            }
    }

}
