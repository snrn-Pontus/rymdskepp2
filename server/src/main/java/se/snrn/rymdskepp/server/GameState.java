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
    private HashMap<Long, Player> idToPlayerMap;
    private float maxSpeed = 10f;
    private float maxAcceleration = 75.5f;
    private ArrayList<Player> unSpawnedPlayers;

    public static GameState getInstance() {
        return ourInstance;
    }

    private GameState() {
        players = new HashSet<>();
        shipPlayerMap = new HashMap<>();
        idToPlayerMap = new HashMap<>();
        unSpawnedPlayers = new ArrayList<>();
    }

    public HashSet<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getTopList() {
        ArrayList<Player> sortedList = new ArrayList<>(players);
        sortedList.sort(Comparator.comparingInt(Player::getScore).reversed());
        return sortedList;
    }

    public HashMap<Entity, Player> getShipPlayerMap() {
        return shipPlayerMap;
    }

    public void setShipPlayerMap(HashMap<Entity, Player> shipPlayerMap) {
        this.shipPlayerMap = shipPlayerMap;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public HashMap<Long, Player> getIdToPlayerMap() {
        return idToPlayerMap;
    }

    public void setIdToPlayerMap(HashMap<Long, Player> idToPlayerMap) {
        this.idToPlayerMap = idToPlayerMap;
    }

    public float getMaxAcceleration() {
        return maxAcceleration;
    }

    public void setMaxAcceleration(float maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public ArrayList<Player> getUnSpawnedPlayers() {
        return unSpawnedPlayers;
    }

    public void setUnSpawnedPlayers(ArrayList<Player> unSpawnedPlayers) {
        this.unSpawnedPlayers = unSpawnedPlayers;
    }
}
