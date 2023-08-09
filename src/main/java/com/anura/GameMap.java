package com.anura;

import com.anura.main.Helper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GameMap {

    public static JsonObject getMap(){
        Gson gson = new Gson();

        String fileReader = Helper.readFromResourceFile("Location.json");
        return gson.fromJson(fileReader, JsonObject.class);
    }
}