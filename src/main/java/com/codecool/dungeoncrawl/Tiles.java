package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(4, 18));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(20, 7));  //(27, 0)
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("zombie", new Tile(24, 7));
        tileMap.put("ghost", new Tile(26, 6));
        tileMap.put("Key", new Tile(16, 23));
        tileMap.put("Sword", new Tile(0, 29));
        tileMap.put("opendoor", new Tile(9, 16));
        tileMap.put("closeddoor", new Tile(6, 16));
        tileMap.put("stair", new Tile(2, 6));
        tileMap.put("floor_grass", new Tile(5, 0)); //(3,0)
        tileMap.put("rocks", new Tile(5, 2));   //(1,3)
        tileMap.put("trees", new Tile(3, 1));
        tileMap.put("stairs", new Tile(2, 6));
        tileMap.put("winTile", new Tile(8, 5));
        tileMap.put("Health", new Tile(16, 25));
        tileMap.put("closedStair", new Tile(21, 12));
        tileMap.put("water", new Tile(8, 5));
        tileMap.put("bridge", new Tile(4, 6));
        tileMap.put("roof", new Tile(5, 11));
        tileMap.put("tower", new Tile(10, 17));
        tileMap.put("wallthree", new Tile(0, 2));
        tileMap.put("floorthree", new Tile(15, 27));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
