package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Health extends Item {
    public Health(Cell cell){super(cell);}

    @Override
    public String getTileName(){
        return "Health";
    }
}
