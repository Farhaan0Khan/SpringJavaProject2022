package game;

import javax.swing.*;


public class GUI extends JPanel {

    //Variables
    private GameLevel level;
    private final JPanel gamePanel;
    private final JProgressBar healthBar;
    private final JLabel enemiesKilled;

    //Constructors
    public GUI(GameLevel level, Game game) {
        this.level = level;

        //Variables Assigned
        gamePanel= new JPanel();
        healthBar = new JProgressBar(0, 100);
        JButton pauseButton = new JButton(new ImageIcon("data/GUI Buttons/pause.png"));
        JButton quitButton = new JButton(new ImageIcon("data/GUI Buttons/quit.png"));
        JButton playButton = new JButton(new ImageIcon("data/GUI Buttons/play.png"));
        JButton restartButton = new JButton(new ImageIcon("data/GUI Buttons/restart.png"));
        JButton muteButton = new JButton(new ImageIcon("data/GUI Buttons/mute.png"));
        JButton unmuteButton = new JButton(new ImageIcon("data/GUI Buttons/unmute.png"));
        JButton soundUpButton = new JButton(new ImageIcon("data/GUI Buttons/soundup.png"));
        JButton soundDownButton = new JButton(new ImageIcon("data/GUI Buttons/sounddown.png"));
        JButton saveOneButton = new JButton(new ImageIcon("data/GUI Buttons/1.png"));
        JButton saveTwoButton = new JButton(new ImageIcon("data/GUI Buttons/2.png"));
        JButton saveThreeButton = new JButton(new ImageIcon("data/GUI Buttons/3.png"));
        enemiesKilled = new JLabel("Enemies Killed: " + level.getPlayer().getEnemiesKilled());
        updateEnemiesKilled();

        //Buttons made transparent to view images
        makeTransparent(pauseButton);
        makeTransparent(quitButton);
        makeTransparent(playButton);
        makeTransparent(restartButton);
        makeTransparent(muteButton);
        makeTransparent(unmuteButton);
        makeTransparent(soundUpButton);
        makeTransparent(soundDownButton);
        makeTransparent(saveOneButton);
        makeTransparent(saveTwoButton);
        makeTransparent(saveThreeButton);

        //Buttons added to the panel
        gamePanel.add(healthBar);
        gamePanel.add(enemiesKilled);
        gamePanel.add(playButton);
        gamePanel.add(pauseButton);
        gamePanel.add(restartButton);
        gamePanel.add(quitButton);
        gamePanel.add(muteButton);
        gamePanel.add(unmuteButton);
        gamePanel.add(soundUpButton);
        gamePanel.add(soundDownButton);
        gamePanel.add(saveOneButton);
        gamePanel.add(saveTwoButton);
        gamePanel.add(saveThreeButton);

        //Action Listeners
        pauseButton.addActionListener(e -> game.pause());

        playButton.addActionListener(e -> game.play());

        quitButton.addActionListener(e -> game.exit());

        restartButton.addActionListener(e -> game.restart());

        soundDownButton.addActionListener(e -> game.soundDown());

        soundUpButton.addActionListener(e -> game.soundUp());

        muteButton.addActionListener(e -> game.mute());

        unmuteButton.addActionListener(e -> game.unmute());

        saveOneButton.addActionListener(e -> game.saveOneButton());

        saveTwoButton.addActionListener(e -> game.saveTwoButton());

        saveThreeButton.addActionListener(e -> game.saveThreeButton());


    }

    public void makeTransparent(JButton button){
        //Buttons Transparency
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
    }

     public void updateEnemiesKilled() {
        //Method updates the text to show the correct value
        enemiesKilled.setText("Enemies Killed: " + level.getPlayer().getEnemiesKilled());
        enemiesKilled.paintImmediately(enemiesKilled.getVisibleRect());
     }


    //Getters and Setters
    public JPanel getGamePanel(){
        return gamePanel;
    }
    public JProgressBar getHealthBar() { return healthBar; }
    public void updateHealthBar(GameLevel level){
        healthBar.setValue(level.getPlayer().getHealth());
        healthBar.paintImmediately(healthBar.getVisibleRect());
    }
    public void setLevel(GameLevel level) {
        this.level = level;
        updateEnemiesKilled();}
}
