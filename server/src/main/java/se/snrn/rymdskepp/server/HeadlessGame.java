package se.snrn.rymdskepp.server;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import se.snrn.rymdskepp.server.factories.ShipFactory;
import se.snrn.rymdskepp.server.ships.JsonShipFactory;
import se.snrn.rymdskepp.server.systems.*;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class HeadlessGame extends Game {

    private final FPSLogger fpsLogger;
    private Console console;
    private Engine engine;
    private WebSocketServer webSocketServer;
    private GameState gameState;

    private ControlledSystem controlledSystem;
    private ShipFactory shipFactory;

    public HeadlessGame(WebSocketServer webSocketServer, GameState gameState) {
        this.webSocketServer = webSocketServer;
        this.gameState = gameState;





        engine = new PooledEngine();
        JsonShipFactory jsonShipFactory = new JsonShipFactory();

        shipFactory = new ShipFactory(engine,jsonShipFactory.getShips());

        engine.addSystem(new MovementSystem());
        BulletSystem bulletSystem = new BulletSystem(webSocketServer);
        engine.addSystem(bulletSystem);

        controlledSystem = new ControlledSystem(gameState.getPlayers());
        engine.addSystem(controlledSystem);

        engine.addSystem(new WrapAroundSystem());

        engine.addSystem(new BoundsSystem());

        engine.addSystem(new CircleBoundsSystem());

        CollisionSystem collisionSystem = new CollisionSystem(webSocketServer);
        engine.addSystem(collisionSystem);

        engine.addSystem(new AsteroidSystem());

        engine.addSystem(new NetworkSystem(webSocketServer));

        engine.addSystem(new WeaponSystem());

        engine.addSystem(new RespawnSystem());

        engine.addSystem(new PlayerInfoSystem());

        engine.addSystem(new StateSystem());


        engine.addSystem(new GameStateSender(webSocketServer, gameState.getPlayers()));

        webSocketServer.setEngine(engine);

        ShipSignal shipSignal = new ShipSignal();


        console = Console.getInstance();
        fpsLogger = new FPSLogger();
    }


    private void spawnUnSpawned() {
        if (!gameState.getUnSpawnedPlayers().isEmpty()) {
            Player playerToremove = null;
            for (Player player : gameState.getUnSpawnedPlayers()) {
                Entity newShip = shipFactory.createNewShip(engine, player.getId(), player.getName(),player.getShipType());
                console.log("Spawned: " + player.getId());
                player.setSpawned(true);
                player.setShip(newShip);
                gameState.getIdToPlayerMap().put(player.getId(), player);
                controlledSystem.getPlayerHash().put(player.getId(), player.getMovementComponent());
                controlledSystem.getPlayerMap().put(player.getId(), newShip);
                gameState.getShipPlayerMap().put(newShip, player);
                playerToremove = player;
            }
            if (playerToremove != null) {
                gameState.getUnSpawnedPlayers().remove(playerToremove);
                playerToremove = null;
            }
        }
    }


    @Override
    public void create() {

    }


    @Override
    public void render() {
        engine.update(Gdx.graphics.getDeltaTime());
        spawnUnSpawned();
    }
}