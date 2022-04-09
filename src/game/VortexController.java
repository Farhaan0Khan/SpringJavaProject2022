package game;

import city.cs.engine.*;

public class VortexController implements CollisionListener {

    //variables
    private GameLevel level;
    private Game game;

    //constructor
    public VortexController(GameLevel level, Game game){
        this.level = level;
        this.game = game;
    }


    //collision detection that will allow you to move onto the next level
    @Override
    public void collide(CollisionEvent e) {
        //if player collided with vortex and the
        //conditions for completing the level are fulfilled
        //goToNextLevel
        if (e.getOtherBody() instanceof Player && level.isComplete()){
            level.getVortex().getSound().stop();
            game.goToNextLevel();
        }
    }
}