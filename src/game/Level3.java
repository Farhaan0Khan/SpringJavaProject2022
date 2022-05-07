package game;

import city.cs.engine.SoundClip;
import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Level3 extends GameLevel {
    private SoundClip gameMusic;

    //Constructors
    public Level3(Game game) {
        super(game, 10);

        //Play music
        try {
            gameMusic = new SoundClip("data/SoundClips/Background Music/3.wav");   // Open an audio input stream
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
        return "Level3";
    }

    //Condition for the game ending is if they have killed all enemies
    @Override
    public boolean isComplete() {
        if (getPlayer().getEnemiesKilled() == 10)
            return true;
        else
            return false;
    }

    //Paint a different background
    @Override
    public Image paintBackground() {
        Image background = new ImageIcon("data/Backgrounds/Level 3.png").getImage();
        return background;
    }
}