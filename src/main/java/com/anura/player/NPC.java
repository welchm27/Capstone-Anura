package com.anura.player;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class NPC {
    // fields and attributes
    private String name;
    private String npc;
    private String dialog;


    public NPC(String npc) {
        this.npc = npc;
        this.name = "src/main/resources/Character.json";
        this.dialog = readDialogFromJsonFile();
    }


    private String readDialogFromJsonFile(){
        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(name);
            JsonObject jsonObject = gson.fromJson(fileReader, JsonObject.class);

            return jsonObject.get(npc).getAsJsonObject().get("dialog").getAsString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "There's nobody to talk to!";
    }


    // Getter for the npc's
    public String getNpc() {
        return npc;
    }

    public String getDialog() {
        return dialog;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}