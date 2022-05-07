package game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Game {

    //Variables
    private GameLevel level;
    private final GameView view;
    private final PlayerController controller;
    private final GUI gui;
    protected Player p;
    private double musicVolume = 1.0;

    private int restarting = 0;

    public Game() {
        //Level Made
        level = new Level1(this);

        //View Made
        view = new GameView(level, 900, 650);
        view.setZoom(20);
        view.setBackground(level.paintBackground());

        gui = new GUI(level, this);
        gui.updateHealthBar(level);
        view.add(gui.getGamePanel(), BorderLayout.NORTH);

        view.addMouseListener(new MouseHandler(level, view));

        controller = new PlayerController(level.getPlayer());
        view.addKeyListener(controller);

        view.addMouseListener(new GiveFocus(view));

        //View added to the frame (Top Left Window)
        final JFrame frame = new JFrame("Jump Run & Butcher");
        frame.add(view);
        //Application is quitted when X is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        //Frame cannot be resized
        frame.setResizable(false);
        //Frame sized to fit window
        frame.pack();
        //Frame can be visible
        frame.setVisible(true);

        //Game Starts
        level.start();
    }


    public void goToNextLevel(){
        //Level progressed from one to another
        if (level instanceof Level1){
            //Old Player stored from one level and level is stopped
            p = level.getPlayer();
            level.stop();
            //Background Music changes otherwise stop music
            level.getGameMusic().close();

            //New level is created
            level = new Level2(this);
            gui.setLevel(level);
            //View changes in New Level
            view.setWorld(level);
            view.setBackground(level.paintBackground());
            //Controller changed to control new player
            controller.updatePlayer(level.getPlayer());

            //Certain variables are passed from level to level
            level.getPlayer().setHealth(p.getHealth());
            level.getPlayer().setUnlockHero2(p.getUnlockHero2());

            //Game starts in new level
            level.start();

        } else if (level instanceof Level2){
            //Old Player stored from one level and level is stopped
            p = level.getPlayer();
            level.stop();
            //Background Music changes otherwise stop music
            level.getGameMusic().close();

            //New level is created
            level = new Level3(this);
            gui.setLevel(level);
            //View changes in New Level
            view.setWorld(level);
            view.setBackground(level.paintBackground());
            //Controller changed to control new player
            controller.updatePlayer(level.getPlayer());

            //Certain variables are passed from level to level
            level.getPlayer().setHealth(p.getHealth());
            level.getPlayer().setUnlockHero2(p.getUnlockHero2());
            level.getPlayer().setUnlockHero3(p.getUnlockHero3());

            //Game starts in new level
            level.start();

        } else if (level instanceof Level3) {
            //Game over after Level 3
            System.out.println("You Win!");
            System.exit(0);
        }
    }

    public void setLevel(GameLevel newLevel) {
        level.stop();
        //Background Music changes otherwise stop music
        level.getGameMusic().close();

        //New Level created
        level = newLevel;
        gui.setLevel(level);
        //View changes in New Level
        view.setWorld(level);
        view.setBackground(level.paintBackground());
        //Controller changed to control new player
        controller.updatePlayer(level.getPlayer());
        level.start();
    }

    public void pause(){
        view.getWorld().stop();    }

    public void play(){
        view.getWorld().start();    }


    public void restart(){
        restarting = 1;
        if (level instanceof Level1){
            level.stop();
            //Background Music changes otherwise stop music
            level.getGameMusic().close();
            if (level.getGeneratedVortex()){
                level.getVortex().getSound().close();}

            //New level is created when restart is clicked
            level = new Level1(this);
            gui.setLevel(level);
            gui.updateHealthBar(level);
            //View changes in New Level
            view.setWorld(level);
            view.setBackground(level.paintBackground());
            view.getWorld().start();
            restarting = 0;
            //Controller changed to control new player
            controller.updatePlayer(level.getPlayer());

        } else if (level instanceof Level2){
            //Stop the level
            p = level.getPlayer();
            level.stop();
            //Background Music changes otherwise stop music
            level.getGameMusic().close();
            level.getVortex().getSound().close();
            if (level.getGeneratedVortex()){
                level.getVortex().getSound().close();}

            //New level is created when restart is clicked
            level = new Level2(this);
            gui.setLevel(level);
            gui.updateHealthBar(level);
            //View changes in New Level
            view.setWorld(level);
            view.setBackground(level.paintBackground());
            view.getWorld().start();
            restarting = 0;
            //Controller changed to control new player
            controller.updatePlayer(level.getPlayer());

            //Certain Variables passed onto new level
            level.getPlayer().setHealth(p.getHealth());
            level.getPlayer().setUnlockHero2(p.getUnlockHero2());
            level.getPlayer().setUnlockHero3(p.getUnlockHero3());
            level.getPlayer().setHero(p.getHero());

            //Game Starts in new level
            level.start();

        } else if (level instanceof Level3){
            //Stop the level
            p = level.getPlayer();
            level.stop();
            //Background Music changes otherwise stop music
            level.getGameMusic().close();
            if (level.getGeneratedVortex()){
                level.getVortex().getSound().close();}

            //New Level is created
            level = new Level3(this);
            gui.setLevel(level);
            gui.updateHealthBar(level);
            //View changes in New Level
            view.setWorld(level);
            view.setBackground(level.paintBackground());
            view.getWorld().start();
            restarting = 0;
            //Controller changed to control new player
            controller.updatePlayer(level.getPlayer());

            //Certain Variables passed onto new level
            level.getPlayer().setHealth(p.getHealth());
            level.getPlayer().setUnlockHero2(p.getUnlockHero2());
            level.getPlayer().setUnlockHero3(p.getUnlockHero3());
            level.getPlayer().setHero(p.getHero());

            //Game starts new level
            level.start();
        }
    }

    public void mute(){
        level.getGameMusic().pause();
    }

    public void unmute(){
        level.getGameMusic().resume();
    }

    public void soundUp(){
        if (musicVolume <= 1.8) {
            musicVolume = musicVolume + 0.2;
            level.getGameMusic().setVolume(musicVolume);
        }
    }
    public void soundDown(){
        if (musicVolume >= 0.4) {
            musicVolume = musicVolume - 0.2;
            level.getGameMusic().setVolume(musicVolume);
        }
    }


    public void saveOneButton(){
        try {
            setLevel(GameSaverLoader.load(this, "data/saves/fileSaveOne.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTwoButton(){
        try {
            setLevel(GameSaverLoader.load(this, "data/saves/fileSaveTwo.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveThreeButton(){
        try {
            setLevel(GameSaverLoader.load(this, "data/saves/fileSaveThree.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit(){
        System.exit(0);    }

    //Getters and Setters
    public GUI getGUI() {
        return gui;
    }
    public int getRestarting() {
        return restarting;
    }
    public void setRestarting(int r){
        this.restarting = r;
    }

    public static void main(String[] args) {
        //Game is Run
        new Game();
        System.out.print("""
        Controls:
        W - Jump
        A - Move Left
        D - Move Right
        J - Switch Heroes
        L - Attack
        1 - Save 1
        2 - Save 2
        3 - Save 3
                """);
    }
}