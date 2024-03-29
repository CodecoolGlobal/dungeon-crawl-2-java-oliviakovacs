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
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.items.Key;
import com.codecool.dungeoncrawl.logic.items.Sword;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;
import java.sql.SQLException;


public class Main extends Application {
    int CANVAS_SIZE = 20;
    GameMap map = MapLoader.loadMap(1);
    Canvas canvas = new Canvas(CANVAS_SIZE * Tiles.TILE_WIDTH, CANVAS_SIZE * Tiles.TILE_WIDTH);

    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label attackStrengthLabel = new Label();
    Label mapLabel = new Label();
    Button pickUpButton = new Button("Pick up");

    Label playerInventory = new Label("INVENTORY: ");
    GameDatabaseManager dbManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("  "), 0, 0);

        ui.add(new Label("Health: "), 0, 1);
        ui.add(new Label("  "), 0, 2);
        ui.add(new Label("Attack Strength: "), 0, 3);
        ui.add(healthLabel, 1, 1);
        ui.add(attackStrengthLabel, 1, 3);
        ui.add(mapLabel, 1, 14);
        ui.add(new Label("  "), 0, 4);

        ui.add(pickUpButton, 0, 5);
        pickUpButton.setOnAction(mousedown -> {
            map.getPlayer().pickUpItem();
            refresh();
        });
        ui.add(new Label("  "), 0, 6);
        pickUpButton.setFocusTraversable(false);
        ui.add(new Label("INVENTORY:"), 0, 7);
        ui.add(playerInventory, 0, 8);

        // -------------  load game  ------------------
        Button loadButton = new Button("Load Game");
        ui.add(loadButton, 0, 16);
        loadButton.setOnAction(mousedown -> {
            showGetNameModalForGameLoad();
            refresh();
        });

        loadButton.setFocusTraversable(false);

        //--------------  export game ------------------
        Button exportButton = new Button("Export");
        ui.add(exportButton, 0, 19);
        exportButton.setOnAction(mousedown -> {
            refresh();
        });
        exportButton.setFocusTraversable(false);
        //--------------  export game end ----------------

        //--------------  import game ------------------
        Button importButton = new Button("Import");
        ui.add(importButton, 0, 20);
        importButton.setOnAction(mousedown -> {
            refresh();
        });
        importButton.setFocusTraversable(false);
        //--------------  import game end ----------------


        // -------------  load game end ------------------


        // -------------  restart game  ------------------
        Button restartButton = new Button("Restart Game");
        restartButton.setOnAction(mousedown -> {
            map = MapLoader.loadMap(1);
            map.getPlayer().setChangeMap(false);
            map.getPlayer().setHealth(Player.HEALTH);
            map.getPlayer().setAttackStrength(Player.ATTACK_STRENGTH);
            map.getPlayer().setInventory(new ArrayList<Item>());
            refresh();
        });
        ui.add(new Label("  "), 0, 12);
        ui.add(new Label("  "), 0, 13);

        ui.add(new Label("  "), 0, 15);
        ui.add(new Label("Map:"), 0, 14);

        ui.add(new Label("  "), 0, 17);
        ui.add(restartButton, 0, 18);
        restartButton.setFocusTraversable(false);

        // ------------  restart game end -----------------

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        KeyCombination saveMac = new KeyCodeCombination(KeyCode.S, KeyCombination.META_DOWN);
        KeyCombination saveWin = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);


        if (exitCombinationMac.match(keyEvent) || exitCombinationWin.match(keyEvent) || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        } else if (saveMac.match(keyEvent) || saveWin.match(keyEvent)) {
            showModal();

        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                refresh();
                break;
        }

        map.repositionCenter();
        monstersAct(map);

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
        } catch (ConcurrentModificationException e) {
            System.out.println("No monsters on map.");
        }
        for (Actor monster : map.getMonsters()) {
            if (monster instanceof Zombie) {
                ((Zombie) monster).move();
            } else if (monster instanceof Ghost) {
                ((Ghost) monster).move();
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
            mapLabel.setText("YOU WON!!!");
            refresh();
        }
    }


    private void refresh() {
        map.repositionCenter();
        int minX = map.getCenterCell().getX() - CANVAS_SIZE / 2;
        int minY = map.getCenterCell().getY() - CANVAS_SIZE / 2;
        int maxX = map.getCenterCell().getX() + CANVAS_SIZE / 2;
        int maxY = map.getCenterCell().getY() + CANVAS_SIZE / 2;
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x - minX, y - minY);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x - minX, y - minY);
                } else {
                    Tiles.drawTile(context, cell, x - minX, y - minY);
                }
            }
            healthLabel.setText("" + map.getPlayer().getHealth());
            attackStrengthLabel.setText("" + map.getPlayer().getAttackStrength());
            mapLabel.setText("" + map.getPlayer().getPlayerOnMap());
            playerInventory.setText("");
            playerInventory.setText(map.getPlayer().displayInventory());

            if (isPlayerDead(map.getPlayer())) {
                healthLabel.setText("YOU DIED!");
                mapLabel.setText("LOOSER MAP");
            }
        }
    }

    public void changeMap() {
        int previousHealth = map.getPlayer().getHealth();
        int previousAttackStrength = map.getPlayer().getAttackStrength();
        ArrayList<Item> previousInventory = map.getPlayer().getInventory();

        if (map.getPlayer().getChangeMap() == true && map.getPlayer().getPlayerOnMap() == 1) {
            map = MapLoader.loadMap(1);
            map.getPlayer().setPlayerOnMap(2);
        } else if (map.getPlayer().getChangeMap() == true && map.getPlayer().getPlayerOnMap() == 2) {
            map = MapLoader.loadMap(2);
            map.getPlayer().setPlayerOnMap(2);
        } else if (map.getPlayer().getChangeMap() == true && map.getPlayer().getPlayerOnMap() == 3) {
            map = MapLoader.loadMap(3);
            map.getPlayer().setPlayerOnMap(3);
        } else if (map.getPlayer().getChangeMap() == true && map.getPlayer().getPlayerOnMap() == 4) {
            map = MapLoader.loadMap(4);
            map.getPlayer().setPlayerOnMap(0);
            new SoundClipTest("winbanjo.wav");
        } else if (map.getPlayer().getChangeMap() == true && map.getPlayer().getPlayerOnMap() == 5) {
            map = MapLoader.loadMap(5);
            map.getPlayer().setPlayerOnMap(0);
            new SoundClipTest("horn-fail.wav");
        }
        map.getPlayer().setChangeMap(false);
        map.getPlayer().setHealth(previousHealth);
        map.getPlayer().setAttackStrength(previousAttackStrength);
        map.getPlayer().setInventory(previousInventory);
    }


    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }

    private void showModal() {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Save Game");
        Label saveGameName = new Label("Name:");
        TextField textField = new TextField();
        GridPane gridPane = new GridPane();
        Button cancelButton = new Button("Cancel");
        Button saveButton = new Button("Save");
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> stage.close());
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            savePlayerInDb(saveButton, textField.getText());
            stage.close();
        });
        gridPane.setHgap(60);
        gridPane.setVgap(30);
        gridPane.add(saveGameName, 2, 2);
        gridPane.add(textField, 3, 2);
        gridPane.add(cancelButton, 4, 4);
        gridPane.add(saveButton, 2, 4);
        stage.setWidth(600);
        stage.setHeight(300);
        stage.setScene(new Scene(gridPane));
        stage.show();
    }

    private void confirmDialog(Player player) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Name already exists");
        alert.setContentText("Would you like to overwrite?");
        //alert.show();

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dbManager.updatePlayer(player);
            alert.close();
        } else {
            alert.close();
        }
    }



    public void savePlayerInDb(Button saveButton, String name) {
        Integer playerId = dbManager.getPlayerIdByNameManager(name);
        System.out.println(playerId);
        Player player = map.getPlayer();
        player.setName(name);
        System.out.println("player name: :" + player.getName());
        if (playerId == null) {
            dbManager.savePlayer(player);
        } else {
            confirmDialog(player);
        }
    }

    public void loadGame(String chosenName){
        int currentMap = dbManager.getMapByPlayerName(chosenName);
        map = MapLoader.loadMap(currentMap);

       HashMap playerDictionary = dbManager.getPlayerByName(chosenName);
       Player player = map.getPlayer();
       player.setHealth((int) playerDictionary.get("hp"));
       player.getCell().setX((int) playerDictionary.get("x"));
       player.getCell().setY((int) playerDictionary.get("y"));
       player.setAttackStrength((int) playerDictionary.get("attack_strength"));
       player.setPlayerOnMap(currentMap);
       player.setInventory(new ArrayList<Item>());
       for (int i=0; i<(int) playerDictionary.get("sword"); i++) {
           player.addToInventory(new Sword(new Cell(map, 0, 0, CellType.FLOOR)));
       }
       for (int i=0; i<(int) playerDictionary.get("key"); i++) {
           player.addToInventory(new Key(new Cell(map, 0, 0, CellType.FLOOR)));
       }
       //relocate player on map:
       player.movePlayerToPosition(player.getCell().getX(), player.getCell().getY());
       System.out.println("x: " + player.getCell().getX() + ", y: " + player.getCell().getY() );
       refresh();
    }

    private void showGetNameModalForGameLoad() {
        ArrayList<String> names = dbManager.getAllNames();
        System.out.println("Names from db : " +names);

        ListView<String> nameList = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (names);
        nameList.setItems(items);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Load Game");
        Label loadGameName = new Label("Saves:");
        TextField textField = new TextField();
        GridPane gridPane = new GridPane();
        Button cancelButton = new Button("Cancel");
        Button loadButton = new Button("Load Game");
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> stage.close());

        loadButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            String chosenName = nameList.getSelectionModel().getSelectedItem();;
            loadGame(chosenName);
            refresh();
            System.out.println("Clicked the load button");
            stage.close();
        });
        gridPane.setHgap(60);
        gridPane.setVgap(30);
        gridPane.add(loadGameName, 2, 2);
        gridPane.add(nameList, 3, 2);
        gridPane.add(cancelButton, 3, 4);
        gridPane.add(loadButton, 2, 4);
        stage.setWidth(600);
        stage.setHeight(300);
        stage.setScene(new Scene(gridPane));
        stage.show();
    }

}

