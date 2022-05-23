package com.codecool.dungeoncrawl;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


    public class SoundClipTest extends JFrame {

        public SoundClipTest(String fileName) {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle("Test Sound Clip");
            this.setSize(300, 200);
            this.setVisible(false);

            try {
                // Open an audio input stream.
                URL url = this.getClass().getClassLoader().getResource(fileName);
                //URL url = this.getClass().getClassLoader().getResource("punch-02.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                // Get a sound clip resource.
                Clip clip = AudioSystem.getClip();
                // Open audio clip and load samples from the audio input stream.
                clip.open(audioIn);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        public void playSound(String fileName) {
            try {
                // Open an audio input stream.
                URL url = this.getClass().getClassLoader().getResource(fileName);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                // Get a sound clip resource.
                Clip clip = AudioSystem.getClip();
                // Open audio clip and load samples from the audio input stream.
                clip.open(audioIn);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }



    public void playSoundS(String soundFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File f = new File(soundFile);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    }
    // -------------------------- for sound end ----------------------
}
