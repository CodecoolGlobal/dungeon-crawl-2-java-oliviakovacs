package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Zombie extends Actor {
    public Zombie(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "zombie";
    }
}
