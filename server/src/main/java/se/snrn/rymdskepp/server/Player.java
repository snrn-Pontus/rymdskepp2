package se.snrn.rymdskepp.server;

import io.vertx.core.http.ServerWebSocket;
import se.snrn.rymdskepp.ShipType;
import se.snrn.rymdskepp.server.components.MovementComponent;

public class Player {

    private ServerWebSocket webSocket;
    private int port;
    private String name;
    private long id;
    private boolean connected;
    private boolean spawned;
    private int score;

    private MovementComponent movementComponent;
    private ShipType shipType;


    public Player(ServerWebSocket webSocket, int port, String name) {
        this.name = name;
        this.score = 0;

        connected = false;
        spawned = false;
        this.webSocket = webSocket;
        this.port = port;
        this.id = port;
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
        return spawned;
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" + "port=" + port +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", connected=" + connected +
                ", spawned=" + spawned +
                ", shipType=" + shipType +
                '}';
    }

    public MovementComponent getMovementComponent() {
        return this.movementComponent;
    }

    public void setMovementComponent(MovementComponent movementComponent) {
        this.movementComponent = movementComponent;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
