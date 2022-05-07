package game;

import city.cs.engine.*;
import java.awt.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Level1 extends GameLevel {
    private SoundClip gameMusic;

    //Constructors
    public Level1(Game game) {
        super(game, 3);

        //Play Music
        try {
            gameMusic = new SoundClip("data/SoundClips/Background Music/1.wav");   // Open an audio input stream
            gameMusic.loop();  // Set it to continuous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
        gameMusic.setVolume(1.0d);

    }

    //Getter for music
    public SoundClip getGameMusic() {
        return gameMusic;
    }

    @Override
    public String getLevelName() {
        return "Level 1";
    }

    //Condition for the game ending is if they have killed all enemies
    @Override
    public boolean isComplete() {
        if (getPlayer().getEnemiesKilled() == 3)
            return true;
        else
            return false;
    }

    //Paint a different background
    @Override
    public Image paintBackground() {
        Image background = new ImageIcon("data/Backgrounds/Level 1.png").getImage();
        return background;
    }
}
