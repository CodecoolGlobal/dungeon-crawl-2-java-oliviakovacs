package com.codecool.dungeoncrawl.logic.items.door;

import com.codecool.dungeoncrawl.logic.Cell;

public class Opendoor extends Door {

    public Opendoor(Cell cell) {
        super(cell);
    }

    public String getTileName(){
        return "opendoor";
    }

}
