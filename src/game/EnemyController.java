package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;


public class EnemyController implements CollisionListener {

    //Variables
    private Player player;
    public EnemyController(Player p){
        this.player = p;
    }

    @Override
    public void collide(CollisionEvent e) {
        //Collision detection between enemy and hero
        if (e.getOtherBody() instanceof Enemy) {
            if (player.getHealth()<0){
                player.death();
            } else {
                //Player Object destroyed if health is below 0
                player.setHealth(player.getHealth() - ((Enemy) e.getOtherBody()).getDamage());
                player.getWorld().getGame().getGUI().updateHealthBar(player.getWorld());
                player.damaged();
            }
        }
    }

}