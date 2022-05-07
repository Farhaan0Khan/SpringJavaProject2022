package game;

import city.cs.engine.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;


public class Enemy extends Walker {

    private final GameLevel w;

    //Enemy Shape Created
    private static final Shape oneShape = new PolygonShape(-0.22f,0.38f,
            -1.82f,-1.92f, -0.37f,-4.18f, 1.15f,-2.25f);

    //Enemy Images Created
    private static final BodyImage fodder1Attack = new BodyImage("data/Characters/Enemy/attack.gif",10f);
    private static final BodyImage fodder1Death = new BodyImage("data/Characters/Enemy/death.gif",10f);
    private static final BodyImage fodder1Idle = new BodyImage("data/Characters/Enemy/idle.gif",10f);
    private static final BodyImage fodder1Jump = new BodyImage("data/Characters/Enemy/jump.gif",10f);
    private static final BodyImage fodder1Run = new BodyImage("data/Characters/Enemy/run.gif",10f);

    //Enemy Statistics set
    private int health = 75;
    private final int damage = 10;

    //Monster Sound Clip
    private static SoundClip monsterSound;
    static {
        try {
            monsterSound = new SoundClip("data/SoundClips/monster.wav");
        } catch (UnsupportedAudioFileException | IOException |
                LineUnavailableException e) {
            System.out.println(e);
        }

    }

    //Constructors
    public Enemy(GameLevel world) {
        super(world, oneShape);
        addImage(fodder1Idle);
        this.w = world;
        monsterSound.setVolume(0.5);
        monsterSound.loop();
    }

    //this allows the body to go towards the object by comparing it's relative position to the player
    public void followPlayer(){
        //Following along the x-axis
        if (w.getPlayer().getPosition().x > this.getPosition().x){
            this.startWalking(2f);
            this.removeAllImages();
            this.addImage(fodder1Run);
        } else if (w.getPlayer().getPosition().x < this.getPosition().x){
            this.startWalking(-2f);
            this.removeAllImages();
            this.addImage(fodder1Run).flipHorizontal();
        }
        //Following along the y-axis
        if (w.getPlayer().getPosition().y > this.getPosition().y){
            this.jump(10f);
            this.removeAllImages();
            if (w.getPlayer().getPosition().x < this.getPosition().x) {
                this.addImage(fodder1Jump).flipHorizontal();
            } else {
                this.addImage(fodder1Jump);
            }
        }
    }
    //Player gets attacked by enemy if they are within a certain distance
    public void attackPlayer(){
        float distanceX = Math.abs(this.getPosition().x - w.getPlayer().getPosition().x);
        float distanceY = Math.abs(this.getPosition().y - w.getPlayer().getPosition().y);
        if (distanceX<2f&&distanceY<2f){
            this.removeAllImages();
            this.addImage(fodder1Attack);
        }
    }

    //Death Animation
    public void death(){
        monsterSound.stop();
        this.removeAllImages();
        this.addImage(fodder1Death);
        this.destroy();
    }

    //Getters and Setters Initialised
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getDamage() { return damage; }

    public World getW() { return w;}
    public Player getP() { return w.getPlayer();}
}
