package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    STAIRS("stairs"),
    WALL("wall"),
    WIN_TILE("winTile"),
    CLOSED_STAIR("closedStair"),
    OPENDOOR("opendoor"),
    CLOSEDDOOR("closeddoor"),
    FLOOR_GRASS("floor_grass"),
    ROCKS("rocks"),
    TREES("trees"),
    WATER("water"),
    BRIDGE("bridge"),
    ROOF("roof"),
    TOWER("tower"),
    WALLTHREE("wallthree"),
    FLOORTHREE("floorthree");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
