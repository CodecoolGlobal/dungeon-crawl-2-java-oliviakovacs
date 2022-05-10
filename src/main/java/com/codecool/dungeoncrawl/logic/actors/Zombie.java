package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import java.util.Random;

public class Zombie extends Actor {
    public Zombie(Cell cell) {
        super(cell);
        this.setHealth(20);
    }


    public void move() {
        Random myRandom = new Random();
        Cell nextCell;
        while (true) {
            int randomDirection = myRandom.nextInt(4);
            switch (randomDirection) {
                case 0:
                    nextCell = this.getCell().getNeighbor(0, 1);
                    break;
                case 1:
                    nextCell = this.getCell().getNeighbor(0, -1);
                    break;
                case 2:
                    nextCell = this.getCell().getNeighbor(1, 0);
                    break;
                default:
                    nextCell = this.getCell().getNeighbor(-1, 0);
                    break;
            }

            if (nextCell.getActor() == null && nextCell.getType() == CellType.FLOOR) {
                this.getCell().setActor(null);
                nextCell.setActor(this);
                setCell(nextCell);
                break;
            }
        }
    }

    @Override
    public String getTileName() {
        return "zombie";
    }
}
