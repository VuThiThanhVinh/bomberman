package uet.oop.bomberman.sound;
import javax.sound.sampled.*;
import java.nio.file.FileSystems;
import java.io.File;
import java.nio.file.Path;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import sun.applet.Main;


import java.io.IOException;


public class Sound {
        public static void play(String sound) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Clip clip = AudioSystem.getClip();
                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                                Main.class.getResourceAsStream("/sound/" + sound + ".wav"));
                        clip.open(inputStream);
                        clip.start();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            }).start();

        }
}
