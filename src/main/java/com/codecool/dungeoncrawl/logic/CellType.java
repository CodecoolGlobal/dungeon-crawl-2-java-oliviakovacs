package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    STAIRS("stairs"),
    WALL("wall"),
    WIN_TILE("winTile"),
    CLOSED_STAIR("closedStair"),
    OPENDOOR("opendoor"),
    CLOSEDDOOR("closeddoor");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
