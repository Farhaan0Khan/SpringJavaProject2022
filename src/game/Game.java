package game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Game {

    //variables
    private GameLevel level;
    private final GameView view;
    private final PlayerController controller;
    private final GUI gui;
    protected Player p;
    private double musicVolume = 1.0;

    private int restarting = 0;

    public Game() {
        //make the level
        level = new Level1(this);

        //make view
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

        // add the view to a frame (Java top level window)
        final JFrame frame = new JFrame("Basic world");
        frame.add(view);
        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // size the frame to fit the world view
        frame.pack();
        // finally, make the frame visible
        frame.setVisible(true);

        //start the simulation
        level.start();
    }


    public void goToNextLevel(){
        //this will progress from one level to another
        if (level instanceof Level1){
            //store the old player from the level and stop the level
            p = level.getPlayer();
            level.stop();
            //stop the music otherwise it'd play in the background
            level.getGameMusic().close();

            //create the next level and level now refers to new level
            level = new Level2(this);
            gui.setLevel(level);
            //change the view to look into new level
            view.setWorld(level);
            view.setBackground(level.paintBackground());
            //change the controller to control the new player
            controller.updatePlayer(level.getPlayer());

            //pass on certain variables
            level.getPlayer().setHealth(p.getHealth());
            level.getPlayer().setUnlockHero2(p.getUnlockHero2());

            //start the simulation in the new level
            level.start();
        } else if (level instanceof Level2){
            //store the old player from the level and stop the level
            p = level.getPlayer();
            level.stop();
            //stop the music otherwise it'd play in the background
            level.getGameMusic().close();

            //create the next level and level now refers to new level
            level = new Level3(this);
            gui.setLevel(level);
            //change the view to look into new level
            view.setWorld(level);
            view.setBackground(level.paintBackground());
            //change the controller to control the new player
            controller.updatePlayer(level.getPlayer());

            //pass on certain variables
            level.getPlayer().setHealth(p.getHealth());
            level.getPlayer().setUnlockHero2(p.getUnlockHero2());
            level.getPlayer().setUnlockHero3(p.getUnlockHero3());

            //start the simulation in the new level
            level.start();
        } else if (level instanceof Level3) {
            //after Level 3, the game is over so exit
            System.out.println("You Win!");
            System.exit(0);
        }
    }

    public void setLevel(GameLevel newLevel) {
        level.stop();
        //stop the music otherwise it'd play in the background
        level.getGameMusic().close();

        //create the next level and level now refers to new level
        level = newLevel;
        gui.setLevel(level);
        //change the view to look into new level
        view.setWorld(level);
        view.setBackground(level.paintBackground());
        //change the controller to control the new player
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
            //stop the music otherwise it'd play in the background
            level.getGameMusic().close();
            if (level.getGeneratedVortex()){
                level.getVortex().getSound().close();}

            //create the new repeat level and level now refers to new level
            level = new Level1(this);
            gui.setLevel(level);
            gui.updateHealthBar(level);
            //change the view to look into new level
            view.setWorld(level);
            view.setBackground(level.paintBackground());
            view.getWorld().start();
            restarting = 0;
            //change the controller to control the new player
            //and since it's only the first level you don't need to pass on anything
            controller.updatePlayer(level.getPlayer());
        } else if (level instanceof Level2){
            //stop the level
            p = level.getPlayer();
            level.stop();
            //stop the music otherwise it'd play in the background
            level.getGameMusic().close();
            level.getVortex().getSound().close();
            if (level.getGeneratedVortex()){
                level.getVortex().getSound().close();}

            //create the next level and level now refers to new level
            level = new Level2(this);
            gui.setLevel(level);
            gui.updateHealthBar(level);
            //change the view to look into new level
            view.setWorld(level);
            view.setBackground(level.paintBackground());
            view.getWorld().start();
            restarting = 0;
            //change the controller to control the new player
            controller.updatePlayer(level.getPlayer());

            //pass on certain variables that were already stored from the previous level
            level.getPlayer().setHealth(p.getHealth());
            level.getPlayer().setUnlockHero2(p.getUnlockHero2());
            level.getPlayer().setUnlockHero3(p.getUnlockHero3());
            level.getPlayer().setHero(p.getHero());

            //start the simulation in the new level
            level.start();
        } else if (level instanceof Level3){
            //stop the level
            p = level.getPlayer();
            level.stop();
            //stop the music otherwise it'd play in the background
            level.getGameMusic().close();
            if (level.getGeneratedVortex()){
                level.getVortex().getSound().close();}

            //create the next level and level now refers to new level
            level = new Level3(this);
            gui.setLevel(level);
            gui.updateHealthBar(level);
            //change the view to look into new level
            view.setWorld(level);
            view.setBackground(level.paintBackground());
            view.getWorld().start();
            restarting = 0;
            //change the controller to control the new player
            controller.updatePlayer(level.getPlayer());

            //pass on certain variables that were already stored from the previous level
            level.getPlayer().setHealth(p.getHealth());
            level.getPlayer().setUnlockHero2(p.getUnlockHero2());
            level.getPlayer().setUnlockHero3(p.getUnlockHero3());
            level.getPlayer().setHero(p.getHero());

            //start the simulation in the new level
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

    //getters and setters
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
        //run the game :)
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