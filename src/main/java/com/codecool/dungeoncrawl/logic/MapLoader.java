package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.items.Key;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.Stair;
import com.codecool.dungeoncrawl.logic.items.Sword;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.items.door.Closeddoor;
import com.codecool.dungeoncrawl.logic.items.door.Door;
import com.codecool.dungeoncrawl.logic.actors.Zombie;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(int playerOnMap) {
        InputStream is;
        if (playerOnMap == 1) {
            is = MapLoader.class.getResourceAsStream("/map.txt");
        } else {
            is = MapLoader.class.getResourceAsStream("/mapTwo.txt");
        }
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'w':
                            cell.setType(CellType.WIN_TILE);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            map.addMonsterToMap(new Skeleton(cell));
                            break;
                        case 'z':
                            cell.setType(CellType.FLOOR);
                            new Zombie(cell);
                            map.addMonsterToMap(new Zombie(cell));
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            map.addMonsterToMap(new Ghost(cell));
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case 'm':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'c':
                            cell.setType(CellType.WALL);
                            new Closeddoor(cell);
                            break;
                        case 'r':
                            cell.setType(CellType.STAIRS);
                            break;
                        case '-':
                            cell.setType(CellType.FLOORTWO);
                            break;
                        case '>':
                            cell.setType(CellType.WALLTWO);
                            break;
                        case '*':
                            cell.setType(CellType.WATER);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
