package game;

import city.cs.engine.*;

public class MagicController implements CollisionListener {
    //variables
    private Magic m;
    private Enemy enemy;

    //constructor
    public MagicController(Magic m){
        this.m = m;
    }


    //collision detection that will allow you to shoot enemies
    @Override
    public void collide(CollisionEvent e) {
        //dependant on enemy health, they will either get damaged or destroyed
        if (e.getOtherBody() instanceof Enemy) {
            enemy = (Enemy) e.getOtherBody();
            int damage = enemy.getHealth() - enemy.getP().getDamage();
            enemy.setHealth(damage);
            if (enemy.getHealth()<0){
                enemy.death();
                enemy.getP().setEnemiesKilled(enemy.getP().getEnemiesKilled()+1);
                enemy.getP().getWorld().getGame().getGUI().updateEnemiesKilled();
            }
            //destroy the bullet
            m.destroy();
        }
        //destroy the bullet if it hits anything else with to prevent the world from getting filled
        if (e.getOtherBody() instanceof StaticBody || e.getOtherBody() instanceof Chest
                || e.getOtherBody() instanceof Vortex) {
            m.destroy();
        }
    }

}