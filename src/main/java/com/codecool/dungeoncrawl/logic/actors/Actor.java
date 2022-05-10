package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health;
    private int attackStrength;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public abstract void move();

//    public void move(int dx, int dy) {
//        Cell nextCell = cell.getNeighbor(dx, dy);
//        if(nextCell.getType() == CellType.FLOOR){
//            if (nextCell.getActor() == null) {
//                cell.setActor(null);
//                nextCell.setActor(this);
//                cell = nextCell;
//            } else {                               //...if there is a monster on the cell:
//                attack(nextCell);
//            }
//        }
//    }

    public void attack(Cell nextCell) {
        System.out.println("FIGHT!!!!");
        Actor monster = nextCell.getActor();
        monster.setHealth(monster.getHealth()-this.getAttackStrength());
        if (monster.getHealth()>0) {                     // ...if the monster survived, it fights back:
            System.out.println("Monster fights back!");
            this.setHealth(this.getHealth()-monster.getAttackStrength());
            if (this.getHealth()<=0) {                   // ...if player dies:
                System.out.println("You died! Game over.");
            } else {                                     // ...if player survived:
                System.out.println("Both survived.");
            }
        } else {                                         // ...if the monster died:
            System.out.println("You killed the monster!!");
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }
}
