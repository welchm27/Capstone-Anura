package com.anura.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Objects;

class Music {

    private static Clip backgroundClip;
    private static Float volume = 1.0f; // Default max volume min is 0.0/stop

    public static synchronized void playBGMusic(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    stopBackgroundMusic();
                    backgroundClip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Objects.requireNonNull(GameLogic.class.getResourceAsStream("/ShumbaTest.wav")));
                    backgroundClip.open(inputStream);
                    setVolume(volume);
                    backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    public static synchronized void playFX(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    stopBackgroundMusic();
                    backgroundClip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Objects.requireNonNull(GameLogic.class.getResourceAsStream("/Good.wav")));
                    backgroundClip.open(inputStream);
                    setVolume(volume);
                    backgroundClip.start();
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