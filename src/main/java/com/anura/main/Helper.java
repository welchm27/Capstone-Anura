package com.anura.main;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Helper {

    // read a txt file, and print it in bright color
    public static void printFileInBrightColor(String file, Ansi.Color color){

        String content = "";
        try {
            Path filePath = Path.of(file);
            content = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Ansi ansi = new Ansi();
        System.out.println(ansi.fgBright(color).a(content).reset());
    }

    // print String in bright color
    public static void printColor(String string, Ansi.Color color) {
        Ansi ansi = new Ansi();
        System.out.print(ansi.fgBright(color).a(string).reset());
    }
}