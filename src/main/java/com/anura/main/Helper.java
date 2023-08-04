package com.anura.main;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Helper {

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