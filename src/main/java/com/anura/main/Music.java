package com.anura.main;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Objects;

class Music {

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            //Main.class.getResourceAsStream("/src/main/resources/ShumbaTest.wav"));  //OG format
                            Objects.requireNonNull(Main.class.getResourceAsStream(
                                    "/src/main/resources/ShumbaTest.wav")));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}