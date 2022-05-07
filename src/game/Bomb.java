package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Bomb implements ActionListener {

    //Variables Created
    private final GameLevel w;
    private final DynamicBody bomb;
    private DynamicBody explosion;
    private static final BodyImage bombImage = new BodyImage("data/SFX/bomb.png", 1f);
    private static final BodyImage explosionImage = new BodyImage("data/SFX/explosion.gif", 10f);

    private static Vec2 position;

    private static final Vec2 flip = new Vec2(0.5f,0.5f);
    private int timerCount = 0;

    //Explosion
    private static SoundClip explode;
    static {
        try {
            explode = new SoundClip("data/SoundClips/bomb.wav");
        } catch (UnsupportedAudioFileException | IOException |
                LineUnavailableException e) {
            System.out.println(e);
        }

    }
    //Creates a bomb object that is 'thrown' by the player

    //Constructors
    public Bomb(GameLevel w){
        //Creating Bomb
        this.w = w;
        bomb = new DynamicBody(w, new CircleShape(0.5f));
        bomb.addImage(bombImage);

        //Creating Bomb slightly to the left to avoid polygon overlapping
        if (w.getPlayer().getFlip()) {
            position = (new Vec2(w.getPlayer().getPosition())).sub(flip);
        } else {
            position = new Vec2(w.getPlayer().getPosition());
        }
        bomb.setPosition(position);

        //Bomb direction changes to which direction the character faces
        Vec2 impulse;
        if (w.getPlayer().getFlip()) {
            impulse = new Vec2(-5,0f);
        } else {
            impulse = new Vec2(5,0f);
        }

        //Application of Bomb Throw
        bomb.applyImpulse(impulse);

        //Timer Created and Started when Bomb gets thrown
        Timer timer = new Timer(2500, this);
        timer.start();
        timer.setRepeats(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Starting Timer when Bomb is created
        timerCount++;
        if (timerCount == 1) {
            //add 1 so it knows it has created a bomb
            position = bomb.getPosition();
            bomb.destroy();
            //create an explosion where the bomb once was
            explode.play();
            explosion = new DynamicBody(w, new CircleShape(5f));
            explosion.addImage(explosionImage);
            explosion.setPosition(position);

            //Timer ended to remove Bomb Explosion
            Timer timer = new Timer(500, this);
            timer.setRepeats(false);
            timer.start();

            //Collision listener added to damage Enemies near by
            explosion.addCollisionListener(e1 -> {
                //Enemy Collision = Damaged
                if (e1.getOtherBody() instanceof Enemy) {
                    Enemy enemy = (Enemy) e1.getOtherBody();
                    int damage = enemy.getHealth() - 90;
                    enemy.setHealth(damage);
                    if (enemy.getHealth()<0){
                        enemy.death();
                        enemy.getP().setEnemiesKilled(enemy.getP().getEnemiesKilled()+1);
                        enemy.getP().getWorld().getGame().getGUI().updateEnemiesKilled();
                    }
                }
                //Player Collision = Damaged
                if (e1.getOtherBody() instanceof Player){
                    Player player = (Player) e1.getOtherBody();
                    player.setHealth(player.getHealth() - 30);
                    if (player.getHealth()<0){
                        player.death();
                    }
                }
            });
        }

        //Explosion removed after timer has ended
        if (timerCount == 2) {
            explosion.destroy();
            timerCount = 0;
        }
    }
}