package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import se.snrn.rymdskepp.systems.NetworkSystem;

import java.util.ArrayList;

public class Rymdskepp extends Game {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private Batch batch;
    public LobbyScreen lobbyScreen;
    private Engine engine;
    public GameScreen gameScreen;


    private ArrayList<NewPlayerConnected> playersToSpawn;

    @Override
    public void create() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        engine = new PooledEngine();

        engine.addSystem(new NetworkSystem());
        playersToSpawn = new ArrayList<>();

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
}