package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Magic extends DynamicBody {

    //variables
    private static final Shape attackShape = new CircleShape(0.5f);
    private static final BodyImage attack = new BodyImage("data/effects/effect1.gif", 3f);
    private static Vec2 impulse;

    private static Vec2 position;

    private static Vec2 flip = new Vec2(0.5f,0.5f);

    //constructor
    public Magic(GameLevel w) {
        super(w,attackShape);
        addImage(attack);
        //set the gravity to 0 to make it go in a straight line
        this.setGravityScale(0f);

        //create it slightly to the left if the player faces left due to polygon overlapping
        if (w.getPlayer().getFlip()) {
            position = (new Vec2(w.getPlayer().getPosition())).sub(flip);
        } else {
            position = new Vec2(w.getPlayer().getPosition());
        }
        this.setPosition(position);

        //set it to be a bullet
        this.isBullet();

        //change impulse direction depending on which way the character faces
        if (w.getPlayer().getFlip()) {
            impulse = new Vec2(-13f,0f);
        } else {
            impulse = new Vec2(13f,0f);
        }
        //apply the impulse
        this.applyImpulse(impulse);
    }
}