package com.codecool.dungeoncrawl.logic;

import com.sun.java.accessibility.util.TopLevelWindowListener;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    STAIRS("stairs"),
    WALL("wall"),
    WIN_TILE("winTile"),
    CLOSED_STAIR("closedStair"),
    OPENDOOR("opendoor"),
    CLOSEDDOOR("closeddoor"),
    FLOORTWO("floortwo"),
    WALLTWO("walltwo"),
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
