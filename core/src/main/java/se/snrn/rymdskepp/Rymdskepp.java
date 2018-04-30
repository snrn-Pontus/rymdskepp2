package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.snrn.rymdskepp.systems.ClientNetworkSystem;

import java.util.ArrayList;
import java.util.HashMap;

public class Rymdskepp extends Game {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private Batch batch;
    public LobbyScreen lobbyScreen;
    private Engine engine;
    public GameScreen gameScreen;


    private ArrayList<NewPlayerConnected> playersToSpawn;
    private ArrayList<NetworkObject> bulletsToSpawn;
    public static String url;
    private HashMap<Long, Entity> players;

    public Rymdskepp(String url) {
        Rymdskepp.url = url;
    }

    @Override
    public void create() {
        players = new HashMap<>();
        engine = new PooledEngine();
        ClientNetworkSystem clientNetworkSystem = new ClientNetworkSystem(this);
        engine.addSystem(clientNetworkSystem);
        playersToSpawn = new ArrayList<>();
        bulletsToSpawn = new ArrayList<>();

        batch = new SpriteBatch();
        lobbyScreen = new LobbyScreen(batch, this, engine);

        setScreen(lobbyScreen);

    }

    @Override
    public void render() {
        super.render();
    }

    public void disconnected() {
        this.setScreen(lobbyScreen);
    }

    public void joinGame() {
        gameScreen = new GameScreen(this, batch, engine, lobbyScreen.getWebSocketClient());
        setScreen(gameScreen);
    }

    public ArrayList<NewPlayerConnected> getPlayersToSpawn() {
        return playersToSpawn;
    }

    public ArrayList<NetworkObject> getBulletsToSpawn() {
        return bulletsToSpawn;
    }

    public HashMap<Long, Entity> getPlayers() {
        return players;
    }

}