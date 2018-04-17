package se.snrn.rymdskepp.server;

import com.badlogic.ashley.core.Entity;
import io.vertx.core.http.ServerWebSocket;
import se.snrn.rymdskepp.ShipType;
import se.snrn.rymdskepp.server.components.MovementComponent;
import se.snrn.rymdskepp.server.components.PlayerComponent;

public class Player extends Entity {

    private ServerWebSocket webSocket;
    private int port;
    private String name;
    private long id;
    private boolean connected;
    private int score;

    private Entity ship;
    private PlayerComponent playerComponent;
    private boolean spawnTime;

    public Player(ServerWebSocket webSocket, int port, String name, ShipType shipType) {
        this.name = name;
        this.score = 0;

        connected = false;
        this.webSocket = webSocket;
        this.port = port;
        this.id = port;
        playerComponent = new PlayerComponent();
        playerComponent.setScore(0);
        playerComponent.setSpawnTimer(5);
        playerComponent.setName(name);
        playerComponent.setShipType(shipType);
        playerComponent.setSpawned(false);
        add(playerComponent);

    }


    public ServerWebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(ServerWebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isSpawned() {
        return playerComponent.getSpawned();
    }

    public void setSpawned(boolean spawned) {
        playerComponent.setSpawned(spawned);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MovementComponent getMovementComponent() {
        return Mappers.movementMapper.get(ship);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setShip(Entity ship) {
        this.ship = ship;
    }

    public Entity getShip() {
        return ship;
    }

    public int getSpawnTimer() {
        return playerComponent.getSpawnTimer();
    }

    public void setSpawnTimer(int timeToSpawn) {
        playerComponent.setSpawnTimer(timeToSpawn);
    }
}
