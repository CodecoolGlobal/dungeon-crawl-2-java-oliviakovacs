package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Random;

public class Ghost extends Actor {
    public final int HEALTH = 20;
    public static final int ATTACK_STRENGTH = 3;
    private String moveDirection = "right";
    public Ghost(Cell cell) {
        super(cell);
        this.setHealth(HEALTH);
        this.setAttackStrength(ATTACK_STRENGTH);
    }

    public void move()  {
        int width = this.getCell().getGameMap().getWidth();
        int currentX = this.getCell().getX();
        if (currentX == width-1) {
            moveDirection="left";
        } else if (currentX == 0) {
            moveDirection = "right";
        }
        Cell nextCell;
            switch (moveDirection) {
                case "right":
                    nextCell = this.getCell().getNeighbor(1, 0);
                    break;
                case "left":
                    nextCell = this.getCell().getNeighbor(-1, 0);
                    break;
                default:
                    nextCell = this.getCell().getNeighbor(1, 0);
                    break;
            }

            if (nextCell.getActor() == null) {
                if (this.getCell().getSecondActor() != null) {
                    if (this != this.getCell().getSecondActor()) {
                        this.getCell().setActor(this.getCell().getSecondActor());
                    }
                    this.getCell().setSecondActor(null);
                } else {
                    this.getCell().setActor(null);
                }
                nextCell.setActor(this);
                this.setCell(nextCell);
            } else {
                attack(this.getCell(), nextCell);
            }
    }

    @Override
    public String getTileName() {
        return "ghost";
    }
}
