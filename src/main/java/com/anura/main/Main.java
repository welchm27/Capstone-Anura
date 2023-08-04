package com.anura.main;
import com.anura.player.Player;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        GameLogic gameLogic = new GameLogic();
        gameLogic.execute();

//
//        String json = "{\"name\":\"purple frog\"}";
//
//        Gson gson = new Gson();
//
//        Character cha = gson.fromJson(json, Character.class);
//
//        System.out.println(cha.getName());
//
//        cha.setName("Yellow Frog");
//        System.out.println(cha.getName());




    }
}