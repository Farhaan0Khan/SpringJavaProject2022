package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;


public class Vortex extends DynamicBody {

    //Variables
    private static final BodyImage vortexImage = new BodyImage("data/SFX/vortex.gif", 7f);
    private static Shape vortexShape = new CircleShape(3f);

    //Vortex SoundClip
    private static SoundClip vortex;
    static {
        try {
            vortex = new SoundClip("data/SoundClips/vortex.wav");
        } catch (UnsupportedAudioFileException | IOException |
                LineUnavailableException e) {
            System.out.println(e);
        }

    }


    //Constructors
    public Vortex(World w) {
        super(w, vortexShape);
        addImage(vortexImage);
        this.setPosition(new Vec2(0f,-10f));
        vortex.loop();
    }

    //Getter
    public SoundClip getSound(){
        return vortex;
    }
}
