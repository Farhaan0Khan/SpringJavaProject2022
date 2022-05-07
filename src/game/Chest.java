package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import java.util.Random;


public class Chest extends DynamicBody {

    //Chest Shape and Image
    private static final BodyImage chestImage = new BodyImage("data/SFX/Chest.png", 2f);
    private static final Shape chestShape = new BoxShape(1f, 1f);

    //X Co-ordinates Randomly Generated
    Random random = new Random();
    float posX = random.nextInt(20 + 20) - 20;

    //Chest Constructors
    public Chest(World w) {
        super(w, chestShape);
        addImage(chestImage);
        this.setPosition(new Vec2(posX, -14));
    }
}
