package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Zombie;
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

import java.util.ConcurrentModificationException;

public class Main extends Application {
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Button pickUpButton = new Button("Pick up");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(healthLabel, 1, 0);
        ui.add(pickUpButton,0,2);
        pickUpButton.setOnAction(mousedown -> {
            map.getPlayer().pickUpItem();
        });
        pickUpButton.setFocusTraversable(false);

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
            //System.exit(0);
        }
    }

    public void monstersAct(GameMap map) {
//        try {
//            map.removeDeadMonsters();
//        } catch (ConcurrentModificationException e){
//            System.out.println("No monsters on map.");
//        }
        for (Actor monster: map.getMonsters()) {
            if (monster instanceof Zombie) {
                ((Zombie) monster).move();
            } else if (monster instanceof Ghost) {
                ((Ghost) monster).move();
            }
        }
    }

    public Boolean isPlayerDead(Actor player){
        if (player.getHealth() <= 0) {return true;}
        return false;
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

            if (isPlayerDead(map.getPlayer())) {
                healthLabel.setText("YOU DIED!  GAME OVER!" );
            }
        }
    }
}
