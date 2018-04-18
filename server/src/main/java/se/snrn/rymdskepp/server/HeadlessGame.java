package se.snrn.rymdskepp.server;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import se.snrn.rymdskepp.server.factories.ShipFactory;
import se.snrn.rymdskepp.server.systems.*;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class HeadlessGame extends Game {

    private Console console;
    private Engine engine;
    private WebSocketServer webSocketServer;
    private GameState gameState;

    private se.snrn.rymdskepp.server.systems.ControlledSystem controlledSystem;

    public HeadlessGame(WebSocketServer webSocketServer, GameState gameState) {
        this.webSocketServer = webSocketServer;
        this.gameState = gameState;

        engine = new Engine();

        ShipFactory.setEngine(engine);


        engine = new PooledEngine();
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


        webSocketServer.setEngine(engine);

        ShipSignal shipSignal = new ShipSignal();


        console = Console.getInstance();
    }


    private void spawnUnSpawned() {
        for (Player player : gameState.getPlayers()) {
            if (!player.isSpawned()) {
                Entity newShip = ShipFactory.createNewShip(engine, player.getId(), player.getName());
                console.log("Spawned: " + player.getId());
                player.setSpawned(true);
                player.setShip(newShip);
                controlledSystem.getPlayerHash().put(player.getId(), player.getMovementComponent());
                controlledSystem.getPlayerMap().put(player.getId(), newShip);
                gameState.getShipPlayerMap().put(newShip, player);
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