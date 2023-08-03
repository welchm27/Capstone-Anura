package com.anura.main;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

//T0D0: my IntelliJ will not let me import the any of the jsons that i need (see above)..
// there is also a chunk of code below that i attempted to adapt to this file but i cannot confirm functionalitiy..


    public class Parser {
        public static void main(String[] args) {
            // read the parse file
            JSONObject jData = loadJSONData("Parse.json");
            // "Check if it's there/null" -paraphrasing Nadra
            if (jData == null) {
                System.out.println("NOTE: An error occurred while loading required .json file(s), please try again.");
                return;
            }

            // extracting command/synonyms for each array
            Set<String> verbs = extractItems(jData.getJSONArray("get"));
            verbs.addAll(extractItems(jData.getJSONArray("go")));
            verbs.addAll(extractItems(jData.getJSONArray("talk")));
            verbs.addAll(extractItems(jData.getJSONArray("use")));
            verbs.addAll(extractItems(jData.getJSONArray("help")));
            verbs.addAll(extractItems(jData.getJSONArray("quit")));
            //extracting the map locations & items user can interact with
            Set<String> locations = extractItems(jData.getJSONArray("locations"));
            Set<String> items = extractItems(jData.getJSONArray("items"));

            // Get user input
            Scanner scanner = new Scanner(System.in);
            System.out.print("What would you like to do?\n  ");
            String userInput = scanner.nextLine().toLowerCase();

            // Parse user input
            String[] input = userInput.split("\\s+");
            String verb = null;
            String item = null;
            String location = null;

            for (String word : input) {
                if (verbs.contains(word)) {
                    verb = word;
                } else if (locations.contains(word)) {
                    location = word;
                } else if (items.contains(word)) {
                    item = word;
                }
            }

            // Display parsed results
            if (verb != null) {
                System.out.println("Verb: " + verb);
            } else {
                System.out.println("That is not an action Freg can do. If you need a reminder of available commands"
                + " type 'help'");
            }

            if (location != null) {
                System.out.println("Location: " + location);
            } else {
                System.out.println("That location is not a destination that Freg can go to right now");
            }
            if (item != null) {
                System.out.println("Item: " + item);
            } else {
                System.out.println("You do not currently have that item");
            }
        }
//BELOW IS THE PORTION THAT I DO NOT UNDERSTAND THE FUNCTIONALITY OF, but is apparently needed?
        // is this if we want to add shit in-game??
        private static JSONObject loadJSONData(String fileName) {
            try {
                InputStream inputStream = CommandParser.class.getResourceAsStream(fileName);
                JSONTokener tokener = new JSONTokener(inputStream);
                return new JSONObject(tokener);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private static Set<String> extractItems(JSONArray category) {
            Set<String> items = new HashSet<>();
            for (int i = 0; i < category.length(); i++) {
                items.add(category.getString(i).toLowerCase());
            }
            return items;
        }
    }

}