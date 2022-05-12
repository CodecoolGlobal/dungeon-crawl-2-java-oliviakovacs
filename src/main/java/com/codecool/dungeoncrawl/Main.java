package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Zombie;
import com.codecool.dungeoncrawl.logic.items.Item;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Main extends Application {
    GameMap map = MapLoader.loadMap(1);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackStrengthLabel = new Label();
    Button pickUpButton = new Button("Pick up");

    Label playerInventory = new Label("INVENTORY: ");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(new Label("Attack Strength: "), 0, 1);
        ui.add(healthLabel, 1, 0);
        ui.add(attackStrengthLabel, 1, 1);
        ui.add(pickUpButton, 0, 2);
        pickUpButton.setOnAction(mousedown -> {
            map.getPlayer().pickUpItem();
            refresh();
        });
        pickUpButton.setFocusTraversable(false);
        ui.add(new Label("INVENTORY:"), 0, 3);
        ui.add(playerInventory, 0, 4);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);




        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                monstersAct(map);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                monstersAct(map);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                monstersAct(map);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                monstersAct(map);
                refresh();
                break;
        }
        if (isPlayerDead(map.getPlayer())) {
            System.out.println("---------------Here the game will be stopped!!!-------------");
            map.getPlayer().setPlayerOnMap(5);
            map.getPlayer().setChangeMap(true);
            refresh();

        }
        checkForWin();
        changeMap();
    }


    public void monstersAct(GameMap map) {
        try {
            map.removeDeadMonsters();
        } catch (ConcurrentModificationException e){
            System.out.println("No monsters on map.");
        }
        for (Actor monster : map.getMonsters()) {
            if (monster instanceof Zombie) {
                ((Zombie) monster).move();
            } else if (monster instanceof Ghost) {
                ((Ghost) monster).move();
            }
        }
    }

    public Boolean isPlayerDead(Actor player) {
        if (player.getHealth() <= 0) {
            return true;
        }
        return false;
    }

    public void checkForWin() {
        if (map.getPlayer().getCell().getType() == CellType.TOWER) {
            System.out.println("---------------------  YOU WON!!!  -----------------------");
            map.getPlayer().setChangeMap(true);
            map.getPlayer().setPlayerOnMap(4);
            refresh();
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }

            }
            healthLabel.setText("" + map.getPlayer().getHealth());
            attackStrengthLabel.setText("" + map.getPlayer().getAttackStrength());
            playerInventory.setText("");
            playerInventory.setText(map.getPlayer().displayInventory());

            if (isPlayerDead(map.getPlayer())) {
                healthLabel.setText("YOU DIED!");
            }
        }
    }

    public void changeMap() {
        int previousHealth = map.getPlayer().getHealth();
        ArrayList<Item> previousInventory = map.getPlayer().getInventory();

        if (map.getPlayer().getChangeMap() == true && map.getPlayer().getPlayerOnMap() == 1) {
            map = MapLoader.loadMap(1);
            map.getPlayer().setChangeMap(false);
            map.getPlayer().setPlayerOnMap(2);
            map.getPlayer().setHealth(previousHealth);
            map.getPlayer().setInventory(previousInventory);

        } else if (map.getPlayer().getChangeMap() == true && map.getPlayer().getPlayerOnMap() == 2){
            map = MapLoader.loadMap(2);
            map.getPlayer().setChangeMap(false);
            map.getPlayer().setPlayerOnMap(2);
            map.getPlayer().setHealth(previousHealth);
            map.getPlayer().setInventory(previousInventory);


        } else if (map.getPlayer().getChangeMap() == true && map.getPlayer().getPlayerOnMap() == 3){
            map = MapLoader.loadMap(3);
            map.getPlayer().setChangeMap(false);
            map.getPlayer().setHealth(previousHealth);
            map.getPlayer().setInventory(previousInventory);


        }else if (map.getPlayer().getChangeMap() == true && map.getPlayer().getPlayerOnMap() == 4){
            map = MapLoader.loadMap(4);
            map.getPlayer().setChangeMap(false);
            map.getPlayer().setHealth(previousHealth);
            new SoundClipTest("winbanjo.wav");
        } else if (map.getPlayer().getChangeMap() == true && map.getPlayer().getPlayerOnMap() == 5){
            map = MapLoader.loadMap(5);
            map.getPlayer().setChangeMap(false);
            map.getPlayer().setHealth(previousHealth);
            new SoundClipTest("horn-fail.wav");

        }
    }
}
