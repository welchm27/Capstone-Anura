package com.anura.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Objects;

class Music {

    private static Clip backgroundClip;
    private static Float volume = 1.0f; // Default max volume min is 0.0/stop

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    stopBackgroundMusic();
                    backgroundClip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Objects.requireNonNull(Main.class.getResourceAsStream(
                                    "/src/main/resources/ShumbaTest.wav")));
                    backgroundClip.open(inputStream);
                    setVolume(volume);
                    backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    public static void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }

    public static void setVolume(float newVolume) {
        if (newVolume >= 0.0f && newVolume <= 1.0f && backgroundClip != null) {
            volume = newVolume;
            FloatControl gainControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(newVolume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }
}
/*
Merge the following into Game Logic ~ quit,  help, map

        while (!userInput.equals("quit")) {
        // ATM Above line is L59, below line is L80

        if (moveInput.length != 2) {
        if (userInput.equals("quit")) {
        break;
        } else if (userInput.equals("help")) {
        Helper.printFile("Help.txt", Ansi.Color.GREEN);
        } else if (userInput.equals("map")) {
        Helper.printFile("VisualMap.txt", Ansi.Color.GREEN);
        } else if (userInput.equals("music")) {
        handleMusicControls(scanner);
        } else {
        // ATM above line is L87, below is L91
        }
        System.out.println("Enter to continue..");
        scanner.nextLine();
        } else {
        // ATM above line is L93, below is L103
        }
        }

        // Helper print line
        }
//Add the following chunk to the code, commenting out sections for ticket matching.
private void handleMusicControls(Scanner scanner) {
        System.out.println("What would you like to do with the background music? (start/stop/volume)\n ");
        String musicCommand = scanner.nextLine();

        if (musicCommand.equalsIgnoreCase("start")) {
        Music.playSound("/src/main/resources/ShumbaTest.wav");
        } else if (musicCommand.equalsIgnoreCase("stop")) {
        Music.stopBackgroundMusic();
        } else if (musicCommand.equalsIgnoreCase("volume")) {
        System.out.println("Enter volume level (low = 0.0 - 1.0 = high):");
        float volume = Float.parseFloat(scanner.nextLine());
        Music.setVolume(volume);
        } else {
        System.out.println("Invalid music command.");
        }
        }
        }
 */