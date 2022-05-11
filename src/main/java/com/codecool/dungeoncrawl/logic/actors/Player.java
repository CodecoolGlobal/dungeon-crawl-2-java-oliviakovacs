package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.door.Closeddoor;
import com.codecool.dungeoncrawl.logic.items.door.Door;
import com.codecool.dungeoncrawl.logic.items.door.Opendoor;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends Actor {
    private ArrayList<Item> inventory;

//    Key key = new Key();



import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Player extends Actor {

    private ArrayList<Item> inventory;

    public final int HEALTH = 10;
    public final int ATTACK_STRENGTH = 5;


    public Player(Cell cell) {
        super(cell);
        this.setHealth(HEALTH);
        this.setAttackStrength(ATTACK_STRENGTH);
        this.inventory = new ArrayList<>();
    }

    public String getTileName() {
        return "player";
    }


    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public ArrayList getInventory() {
        return inventory;
    }

    public void pickUpItem() {
        if (this.getCell().getItem() != null && !(this.getCell().getItem() instanceof Door)) {

            addToInventory(this.getCell().getItem());
            System.out.println(inventory);
            this.getCell().setItem(null);
        }

    }


    public void move() {};

    public void move(int dx, int dy) {
        Cell cell = getCell();
        Cell nextCell = getCell().getNeighbor(dx, dy);
        if (nextCell.getType() == CellType.FLOOR) {
            if (nextCell.getActor() == null) {
                cell.setActor(null);
                nextCell.setActor(this);
                setCell(nextCell);
            }
        } else if (nextCell.getType() == CellType.WALL && nextCell.getItem() instanceof Closeddoor && nextCell.getActor() == null) {
            int counter = 0;
            for (Item item : inventory) {
                counter += 1;
                if (item instanceof Key) {          //ha van nála key
                    removeFromInventory(item);
                    cell.setActor(null);
                    nextCell.setType(CellType.FLOOR);
                    nextCell.setActor(this);
                    nextCell.setItem(new Opendoor(nextCell));
                    setCell(nextCell);
                    break;
                } else if (inventory.size() == counter) {       //ha nincs nála key
                    System.out.println("Key missing to open door.");
                }
//                if (inventory.contains(instanceof Key)) {
//                  if (inventory.stream().anyMatch(item -> item instanceof Key)) {
            }
        } else {                               //...if there is a monster on the cell:
            attack(nextCell);
        }
    }

    public String displayInventory() {
        StringBuilder display = new StringBuilder();
        int keyCount = 0;
        int swordCount = 0;
        HashMap<String, Integer> inventory_dict = new HashMap<String, Integer>();
        for(Item item : inventory){
            if(item instanceof Key){
                keyCount+=1;
                if(keyCount <= 1){
                    inventory_dict.put(item.getTileName(), keyCount);
                }else{
                    inventory_dict.put("key", keyCount);
                }

            } else if (item instanceof Sword){
                inventory_dict.put(item.getTileName(), swordCount+=1);
            }
        }
        for(HashMap.Entry<String, Integer> element: inventory_dict.entrySet()){
            display.append(element.getKey()+": "+ element.getValue());
            display.append("\n");
        }


        return display.toString();
    }


}
