package game;

import city.cs.engine.*;

public class VortexController implements CollisionListener {

    //Variables
    private GameLevel level;
    private Game game;

    //Constructors
    public VortexController(GameLevel level, Game game){
        this.level = level;
        this.game = game;
    }


    //Collision detection that will allow you to move onto the next level
    @Override
    public void collide(CollisionEvent e) {
        //If player collided with vortex and the conditions for completing the level are fulfilled goToNextLevel
        if (e.getOtherBody() instanceof Player && level.isComplete()){
            level.getVortex().getSound().stop();
            game.goToNextLevel();
        }
    }
}