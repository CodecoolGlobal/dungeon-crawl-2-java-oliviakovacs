package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.Random;

public class Zombie extends Actor {
    public final int HEALTH = 10;
    public static final int ATTACK_STRENGTH = 2;

    public Zombie(Cell cell) {
        super(cell);
        this.setHealth(HEALTH);
        this.setAttackStrength(ATTACK_STRENGTH);
    }


    public void move() {
        Random myRandom = new Random();
        Cell nextCell;
            int randomDirection = myRandom.nextInt(6);
            switch (randomDirection) {
                case 0:
                    move(0,1);
                    break;
                case 1:
                    move(0,-1);
                    break;
                case 2:
                    move(1,0);
                    break;
                case 3:
                    move(-1,0);
                    break;
                default:
                    break;
            }
    }

    @Override
    public String getTileName() {
        return "zombie";
    }
}
