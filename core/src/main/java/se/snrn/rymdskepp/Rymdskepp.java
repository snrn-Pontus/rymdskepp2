package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Logger;
import net.dermetfan.gdx.assets.AnnotationAssetManager;
import se.snrn.rymdskepp.systems.ClientNetworkSystem;

import java.util.ArrayList;
import java.util.HashMap;

import static se.snrn.rymdskepp.LoadingStatus.*;

public class Rymdskepp extends Game {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private Batch batch;
    public LobbyScreen lobbyScreen;
    private Engine engine;
    public GameScreen gameScreen;

    public static final AnnotationAssetManager manager = new AnnotationAssetManager(new InternalFileHandleResolver());
    private LoadingScreen loadingScreen;

    private ArrayList<NewPlayerConnected> playersToSpawn;
    private ArrayList<NetworkObject> bulletsToSpawn;
    public static String url;
    private HashMap<Long, Entity> players;
    private LoadingStatus loadingStatus = LOADING;

    public Rymdskepp(String url) {
        Rymdskepp.url = url;
    }

    @Override
    public void create() {

        manager.load("skin/quantum-horizon-ui.atlas", TextureAtlas.class);
        manager.load("skin/quantum-horizon-ui.json", Skin.class);
        manager.load(Assets.class);
        players = new HashMap<>();
        engine = new PooledEngine();
        ClientNetworkSystem clientNetworkSystem = new ClientNetworkSystem(this);
        engine.addSystem(clientNetworkSystem);
        playersToSpawn = new ArrayList<>();
        bulletsToSpawn = new ArrayList<>();

        batch = new SpriteBatch();

        manager.setLogger(new Logger("assets"));

    }

    @Override
    public void render() {
        if (manager.update() && loadingStatus == MENU_LOADED) {
            System.out.println("set FINISHED_LOADING");
            loadingStatus = LOADED;
            System.out.println("set mainScreen");
            lobbyScreen = new LobbyScreen(batch, this, engine);
            this.setScreen(lobbyScreen);
        }

        if (manager.isLoaded(Assets.skin) && loadingStatus == LOADING) {
            System.out.println("set MENU_LOADED");
            loadingStatus = MENU_LOADED;
            System.out.println("set startScreen");
            loadingScreen = new LoadingScreen(batch);
            this.setScreen(loadingScreen);
        }
        while (!manager.update()) {
            System.out.println(manager.getProgress());
        }
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