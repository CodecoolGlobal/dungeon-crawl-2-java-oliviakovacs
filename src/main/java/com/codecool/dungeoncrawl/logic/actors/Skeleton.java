package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public class Skeleton extends Actor {
    public final int HEALTH = 15;
    public static final int ATTACK_STRENGTH = 1;

    public Skeleton(Cell cell) {
        super(cell);
        this.setHealth(HEALTH);
        this.setAttackStrength(ATTACK_STRENGTH);
    }

    public void move() {}

    @Override
    public String getTileName() {
        return "skeleton";
    }

}
