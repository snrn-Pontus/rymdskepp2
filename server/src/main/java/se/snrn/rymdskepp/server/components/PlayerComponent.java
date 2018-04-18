package se.snrn.rymdskepp.server.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import se.snrn.rymdskepp.ShipType;

import static se.snrn.rymdskepp.ShipType.NONE;

public class PlayerComponent implements Component, Poolable {

    private int score;
    private int spawnTimer;
    private String name;
    private int shipType;
    private boolean spawned;

    public PlayerComponent() {

    }

    @Override
    public void reset() {
        score = 0;
        spawnTimer = 0;
        name = "";
        shipType = 0;
        spawned = false;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSpawnTimer() {
        return spawnTimer;
    }

    public void setSpawnTimer(int spawnTimer) {
        this.spawnTimer = spawnTimer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setShipType(int shipType) {
        this.shipType = shipType;
    }

    public int getShipType() {
        return shipType;
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public boolean getSpawned() {
        return spawned;
    }
}
