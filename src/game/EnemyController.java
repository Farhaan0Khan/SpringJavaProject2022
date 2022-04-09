package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;


public class EnemyController implements CollisionListener {

    //variables
    private Player player;
    public EnemyController(Player p){
        this.player = p;
    }

    @Override
    public void collide(CollisionEvent e) {
        //collision detection if the enemy collides with the player
        //this allows the enemy to hurt the player
        if (e.getOtherBody() instanceof Enemy) {
            if (player.getHealth()<0){
                player.death();
            } else {
                //destroy the player object if their health drops below 0
                player.setHealth(player.getHealth() - ((Enemy) e.getOtherBody()).getDamage());
                player.getWorld().getGame().getGUI().updateHealthBar(player.getWorld());
                player.damaged();
            }
        }
    }

}