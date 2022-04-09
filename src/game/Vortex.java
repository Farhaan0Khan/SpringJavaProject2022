package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;


public class Vortex extends DynamicBody {

    //variables
    private static final BodyImage vortexImage = new BodyImage("data/effects/vortex.gif", 7f);
    private static Shape vortexShape = new CircleShape(3f);

    //the sound of vortex
    private static SoundClip vortex;
    static {
        try {
            vortex = new SoundClip("data/sounds/vortex.wav");
        } catch (UnsupportedAudioFileException | IOException |
                LineUnavailableException e) {
            System.out.println(e);
        }

    }


    //constructor
    public Vortex(World w) {
        super(w, vortexShape);
        addImage(vortexImage);
        this.setPosition(new Vec2(0f,-10f));
        vortex.loop();
    }

    //getter
    public SoundClip getSound(){
        return vortex;
    }
}
