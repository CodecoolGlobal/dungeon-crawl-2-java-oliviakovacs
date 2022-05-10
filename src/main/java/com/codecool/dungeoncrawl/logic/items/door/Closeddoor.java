package com.codecool.dungeoncrawl.logic.items.door;

import com.codecool.dungeoncrawl.logic.Cell;

public class Closeddoor extends Door{

    public Closeddoor(Cell cell) {
        super(cell);
    }

    public String getTileName(){
        return "closeddoor";
    }

}
