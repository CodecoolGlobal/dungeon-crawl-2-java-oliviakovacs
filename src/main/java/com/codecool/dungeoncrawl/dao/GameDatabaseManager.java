package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameDatabaseManager {
    private PlayerDao playerDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }

    public void updatePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.update(model);
    }

    public Integer getPlayerIdByNameManager(String name) {
        System.out.println("im in dbmanager get player id");
        Integer playerId = playerDao.getPlayerIdByName(name);
        return playerId;
    }

    public ArrayList<String> getAllNames() {
        ArrayList<String> names = playerDao.getAllNames();
        return names;
    }

    public HashMap getPlayerByName(String name) {
        HashMap playerDictionary = playerDao.getPlayerByName(name);
        return playerDictionary;
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = "dungeoncrawl";
        String user = "wildzebra";
        String password = "aaaa";

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

    public PlayerDao getPlayerDao() {
        return playerDao;
    }


}
