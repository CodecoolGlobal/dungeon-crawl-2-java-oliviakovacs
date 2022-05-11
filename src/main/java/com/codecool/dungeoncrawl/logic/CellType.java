package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    STAIRS("stairs"),
    WALL("wall"),
    WIN_TILE("win_tile"),
    OPENDOOR("opendoor"),
    CLOSEDDOOR("closeddoor"),
    FLOORTWO("floortwo"),
    WALLTWO("walltwo"),
    WATER("water");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
