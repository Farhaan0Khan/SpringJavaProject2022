package game;

import city.cs.engine.UserView;
import java.awt.*;


public class GameView extends UserView {

    //Variables
    private Image background;
    private GameLevel w;

    //Constructors
    public GameView(GameLevel w, int width, int height) {
        super(w, width, height);
        this.w = w;
    }

    //Allows BG to be changed for each level
    public void setBackground(Image background){
        this.background = background;
    }

    //keep to satisfy IntelliJ
    @Override
    protected void paintForeground(Graphics2D g) {
    }

    //Background drawn with given image
    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, this);
    }
}
