package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
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
