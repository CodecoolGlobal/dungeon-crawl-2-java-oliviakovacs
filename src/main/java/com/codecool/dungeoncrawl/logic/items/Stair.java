package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;


public class Stair extends Item {
    public Stair(Cell cell){super(cell);}

    @Override
    public String getTileName(){
        return "stair";
    }
}
