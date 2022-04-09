package game;

import city.cs.engine.*;
import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


public class Level2 extends GameLevel {
    public SoundClip gameMusic;

    //constructor
    public Level2(Game game) {
        super(game, 6);

        //play music
        try {
            gameMusic = new SoundClip("data/sounds/bg/2.wav");   // Open an audio input stream
            gameMusic.loop();  // Set it to continuous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
        gameMusic.setVolume(1.0d);
    }

    //getter for music
    public SoundClip getGameMusic() {
        return gameMusic;
    }

    //lets us know what level this is
    @Override
    public String getLevelName() {
        return "Level2";
    }

    //condition for the game ending is if they have killed all enemies
    @Override
    public boolean isComplete() {
        if (getPlayer().getEnemiesKilled() == 6){
            return true;
        } else {
            return false;
        }
    }

    //paint a different background
    @Override
    public Image paintBackground() {
        Image background = new ImageIcon("data/bg/bg2.png").getImage();
        return background;
    }
}