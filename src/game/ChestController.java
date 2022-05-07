package game;

import city.cs.engine.*;


public class ChestController implements CollisionListener {

    //Collision Listener Variables
    private final Chest c;

    //Constructors
    public ChestController(Chest c){ this.c = c; }

    @Override
    public void collide(CollisionEvent e) {
        //Character is  unlocked once collision is detecte
        if (e.getOtherBody() instanceof Player) {
            Player p = (Player) e.getOtherBody();
            //If 2nd hero isn't unlcoked in the first level then unlock automatically
            if (!p.getUnlockHero2() && p.getWorld() instanceof Level1){
                p.setUnlockHero2(true);
            }
            //If 3rd hero isn't unlcoked in the second level then unlock automatically
            if (!p.getUnlockHero3() && p.getWorld() instanceof Level2) {
                p.setUnlockHero3(true);
            }
            if (p.getWorld() instanceof Level3) {
                if (p.getHealth()<51){
                    p.setHealth(p.getHealth()+50);
                }
            }
            //Chest destroyed once picked up
            c.destroy();
            p.getWorld().setPickedUp(true);
        }
    }
}
