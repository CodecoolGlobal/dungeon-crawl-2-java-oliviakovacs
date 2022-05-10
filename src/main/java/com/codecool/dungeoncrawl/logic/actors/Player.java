package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.Item;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends Actor {

    private ArrayList<Item> inventory;

    public Player(Cell cell) {
        super(cell);
        inventory = new ArrayList<>();
    public final int HEALTH = 10;
    public final int ATTACK_STRENGTH = 5;

    public Player(Cell cell) {
        super(cell);
        this.setHealth(HEALTH);
        this.setAttackStrength(ATTACK_STRENGTH);
    }

    public String getTileName() {
        return "player";
    }

    public void addToInventory(Item item){
        inventory.add(item);
    }

    public void removeFromInventory(Item item){
        inventory.remove(item);
    }

    public ArrayList getInventory(){
        return inventory;
    }

    public void pickUpItem(){
        if(this.getCell().getItem() != null){
            addToInventory(this.getCell().getItem());
            System.out.println(inventory);
            this.getCell().setItem(null);
        }

    }

}
