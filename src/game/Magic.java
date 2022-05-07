package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Magic extends DynamicBody {

    //Variables
    private static final Shape attackShape = new CircleShape(0.5f);
    private static final BodyImage attack = new BodyImage("data/SFX/effect1.gif", 3f);
    private static Vec2 impulse;

    private static Vec2 position;

    private static Vec2 flip = new Vec2(0.5f,0.5f);

    //Constructors
    public Magic(GameLevel w) {
        super(w,attackShape);
        addImage(attack);
        //Gravity Set to 0 (Allows straight line movement)
        this.setGravityScale(0f);

        //Creating it slightly to the left to avoid polygon overlapping
        if (w.getPlayer().getFlip()) {
            position = (new Vec2(w.getPlayer().getPosition())).sub(flip);
        } else {
            position = new Vec2(w.getPlayer().getPosition());
        }
        this.setPosition(position);

        //Set it to be a bullet
        this.isBullet();

        //Impulse direction changes according to which way the character faces
        if (w.getPlayer().getFlip()) {
            impulse = new Vec2(-13f,0f);
        } else {
            impulse = new Vec2(13f,0f);
        }
        //Impulse Applied
        this.applyImpulse(impulse);
    }
}