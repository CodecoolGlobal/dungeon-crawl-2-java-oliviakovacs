package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Player extends Actor {
    public final int HEALTH = 10;
    public final int ATTACK_STRENGTH = 5;

    public Player(Cell cell) {
        super(cell);
        this.setHealth(HEALTH);
        this.setAttackStrength(ATTACK_STRENGTH);
    }

    public String getTileName() {
        return "player";
    }
}
