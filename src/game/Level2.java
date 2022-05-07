package game;

import city.cs.engine.*;
import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Level2 extends GameLevel {
    public SoundClip gameMusic;

    //Constructors
    public Level2(Game game) {
        super(game, 6);

        //Play music
        try {
            gameMusic = new SoundClip("data/SoundClips/Background Music/2.wav");   // Open an audio input stream
            gameMusic.loop();  // Set it to continuous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
        gameMusic.setVolume(1.0d);
    }

    //Getter for Music
    public SoundClip getGameMusic() {
        return gameMusic;
    }

    @Override
    public String getLevelName() {
        return "Level2";
    }

    //Condition for the game ending is if they have killed all enemies
    @Override
    public boolean isComplete() {
        if (getPlayer().getEnemiesKilled() == 6){
            return true;
        } else {
            return false;
        }
    }

    //Paint a different background
    @Override
    public Image paintBackground() {
        Image background = new ImageIcon("data/Backgrounds/Level 2.png").getImage();
        return background;
    }
}