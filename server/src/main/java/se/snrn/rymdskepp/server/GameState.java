package se.snrn.rymdskepp.server;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class GameState {
    private static GameState ourInstance = new GameState();


    private final HashSet<Player> players;
    private HashMap<Entity, Player> shipPlayerMap;

    public static GameState getInstance() {
        return ourInstance;
    }

    private GameState() {
        players = new HashSet<>();
        shipPlayerMap = new HashMap<>();
    }

    public HashSet<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getTopList() {
        ArrayList<Player> sortedList = new ArrayList<>(players);
        sortedList.sort(Comparator.comparingInt(Player::getScore));
        return sortedList;
    }

    public HashMap<Entity, Player> getShipPlayerMap() {
        return shipPlayerMap;
    }

    public void setShipPlayerMap(HashMap<Entity, Player> shipPlayerMap) {
        this.shipPlayerMap = shipPlayerMap;
    }
}
