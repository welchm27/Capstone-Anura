package com.anura;

import com.anura.main.Helper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GameObject {
    // fields and attributes
    private String name;
    private String item;
    private String description;

    public GameObject(String type, String item) {
        this.name = type;
        this.item = item;
        this.description = readDescriptionFromJsonFile();
    }

    private String readDescriptionFromJsonFile() {
        try {
            Gson gson = new Gson();
            String contentFile = Helper.readFromResourceFile(getJsonFilePath(name));
            JsonObject jsonObject = gson.fromJson(contentFile, JsonObject.class);

            return jsonObject.get(item).getAsJsonObject().get("desc").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Description not found!";
    }

    private String getJsonFilePath(String objectType) {
        // String basePath = "src/main/resources/";
        String itemName;

        switch (objectType.toLowerCase()) {
            case "item":
                itemName = "Item.json";
                break;
            case "location":
                itemName = "location.json";
                break;
            case "food":
                itemName = "food.json";
                break;
            default:
                throw new IllegalArgumentException("Invalid object type: " + objectType);
        }

        return itemName;
    }

    // Getter for the description
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name;
    }
}