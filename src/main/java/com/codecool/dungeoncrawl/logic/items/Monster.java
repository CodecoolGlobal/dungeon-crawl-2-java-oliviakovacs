package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Monster extends Item {
    public Monster(Cell cell){super(cell);}

    @Override
    public String getTileName(){
        return "monster";
    }
}
