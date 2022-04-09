package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerController implements KeyListener {

    //variables
    private Player player;

    private List<Enemy> EnemyList = new ArrayList<>();
    private GameSaverLoader gameSaverLoader = new GameSaverLoader();

    private boolean spamPrevention;

    //constructor
    public PlayerController(Player p) {
        player = p;
    }

    //keep to satisfy IJ
    @Override
    public void keyTyped(KeyEvent e) {
    }


    public void keyPressed(KeyEvent e) {
        //getting the key pressed
        int key = e.getKeyCode();

        //updating objects in the game
        updateGame();

        //allowing the user to save in different files
        if (key == KeyEvent.VK_1) {
            try {
                gameSaverLoader.save(player.getWorld(), "data/saves/fileSaveOne.txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.out.println("Saving in 1...");
        }
        if (key == KeyEvent.VK_2) {
            try {
                gameSaverLoader.save(player.getWorld(), "data/saves/fileSaveTwo.txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.out.println("Saving in 2...");
        }
        if (key == KeyEvent.VK_3) {
            try {
                gameSaverLoader.save(player.getWorld(), "data/saves/fileSaveThree.txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.out.println("Saving in 3...");
        }

        //allowing the player to switch heroes depending if conditions are met
        if (key == KeyEvent.VK_J) {
            int i = player.getHero()+1;
            if (player.getUnlockHero2() && i == 2){
                player.setHero(2);
                player.idle();
            } else if (player.getUnlockHero3() && i == 3){
                player.setHero(3);
                player.idle();
            } else {
                player.setHero(1);
                player.idle();
            }
        }

        //allowing the player to control using WAD
        if (key == KeyEvent.VK_W) {
            player.jumpUp();
            player.jump(player.getJumpingSpeed());
        } else if (key == KeyEvent.VK_A) {
            player.setFlip(true);
            player.runLeft();
            player.startWalking(-player.getWalkingSpeed());
        } else if (key == KeyEvent.VK_D) {
            player.setFlip(false);
            player.runRight();
            player.startWalking(player.getWalkingSpeed());
        }

        //allowing the player to attack differently based on who they are
        if (key == KeyEvent.VK_L && player.getHero() == 1 && !spamPrevention) {
            player.attack();
            Magic attack = new Magic(player.getWorld());
            MagicController control = new MagicController(attack);
            attack.addCollisionListener(control);
            spamPrevention = true;
        } else if (key == KeyEvent.VK_L && player.getHero() == 2 && !spamPrevention) {
            swordAttack(2f);
            spamPrevention = true;
        } else if (key == KeyEvent.VK_L && player.getHero() == 3 && !spamPrevention && player.getBombsLaunched() < 3) {
            bombAttack();
            player.setBombsLaunched(player.getBombsLaunched()+1);
            spamPrevention = true;
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        //getting the key
        int key = e.getKeyCode();

        //updating objects in the game
        updateGame();

        //preventing the player walking without pressing a key
        if (key == KeyEvent.VK_W) {
            player.idle();
        } else if (key == KeyEvent.VK_A) {
            player.stopWalking();
            player.idle();
        } else if (key == KeyEvent.VK_D) {
            player.stopWalking();
            player.idle();
        }  else if (key == KeyEvent.VK_L){
            player.idle();
        }

        if (key == KeyEvent.VK_L){
            spamPrevention = false;
        }
    }


    protected void updateGame(){
        //every time a key is pressed, update where the player to the enemy
        EnemyList = player.getWorld().getEnemy();
        for (int i = 0; i < EnemyList.size(); i++) {
            EnemyList.get(i).followPlayer();
            EnemyList.get(i).attackPlayer();
        }

        //continuously run as they have built in validation preventing it happening all the time
        player.getWorld().generateChest();
        player.getWorld().generateVortex();
    }


    //another style of attack for the player
    protected void swordAttack(float swordSpan) {
        //change image
        player.attack();
        for (int i = 0; i < EnemyList.size(); i++) {
            Enemy enemy = EnemyList.get(i);
            float distanceX = Math.abs(enemy.getPosition().x - player.getPosition().x);
            float distanceY = Math.abs(enemy.getPosition().y - player.getPosition().y);
            //compare distance of all enemies to player
            if (distanceX < swordSpan && distanceY < swordSpan) {
                int damage = enemy.getHealth() - player.getDamage();
                enemy.setHealth(damage);
                //if the enemy health is below 0, destroy the object and add one to kill count
                if (enemy.getHealth() < 0) {
                    enemy.death();
                    player.setEnemiesKilled(player.getEnemiesKilled() + 1);
                    player.getWorld().getGame().getGUI().updateEnemiesKilled();
                }
            }
        }
    }


    protected void bombAttack(){
        //change image
        player.attack();
        Bomb b = new Bomb(player.getWorld());
    }

    //setter
    public void updatePlayer(Player player) { this.player = player; }
}
