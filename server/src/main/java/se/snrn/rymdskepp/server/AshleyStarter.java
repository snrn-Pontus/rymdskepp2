package se.snrn.rymdskepp.server;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import se.snrn.rymdskepp.server.factories.ShipFactory;
import se.snrn.rymdskepp.server.systems.AsteroidSystem;
import se.snrn.rymdskepp.server.systems.CollisionSystem;
import se.snrn.rymdskepp.server.systems.MovementSystem;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class AshleyStarter implements Runnable {

    private Console console;
    private Engine engine;
    private WebSocketServer webSocketServer;
    private GameState gameState;

    private se.snrn.rymdskepp.server.systems.ControlledSystem controlledSystem;

    public AshleyStarter(WebSocketServer webSocketServer, GameState gameState) {
        this.webSocketServer = webSocketServer;
        this.gameState = gameState;

        engine = new Engine();

        ShipFactory.setEngine(engine);


        engine = new PooledEngine();
        engine.addSystem(new MovementSystem());
        se.snrn.rymdskepp.server.systems.BulletSystem bulletSystem = new se.snrn.rymdskepp.server.systems.BulletSystem(webSocketServer);
        engine.addSystem(bulletSystem);

        controlledSystem = new se.snrn.rymdskepp.server.systems.ControlledSystem(gameState.getPlayers());
        engine.addSystem(controlledSystem);

        engine.addSystem(new se.snrn.rymdskepp.server.systems.WrapAroundSystem());

        engine.addSystem(new se.snrn.rymdskepp.server.systems.BoundsSystem());

        engine.addSystem(new se.snrn.rymdskepp.server.systems.CircleBoundsSystem());
        se.snrn.rymdskepp.server.systems.CollisionSystem collisionSystem = new CollisionSystem(webSocketServer);
        engine.addSystem(collisionSystem);

        engine.addSystem(new AsteroidSystem());

        engine.addSystem(new se.snrn.rymdskepp.server.systems.NetworkSystem(webSocketServer));

        engine.addSystem(new se.snrn.rymdskepp.server.systems.WeaponSystem());


        webSocketServer.setEngine(engine);

        console = Console.getInstance();
    }

    private void update(float deltaTime) {
        spawnUnSpawned();
        engine.update(deltaTime);


        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void spawnUnSpawned() {
        for (Player player : gameState.getPlayers()) {
            if (!player.isSpawned()) {
                Entity newShip = ShipFactory.createNewShip(engine, player.getId(), player.getName());
                console.log("Spawned: " + player.getId());
                player.setSpawned(true);
                player.setMovementComponent(Mappers.movementMapper.get(newShip));
                controlledSystem.getPlayerHash().put(player.getId(), player.getMovementComponent());
                controlledSystem.getPlayerMap().put(player.getId(), newShip);
            }
        }
    }

    @Override
    public void run() {
        long last_time = System.nanoTime();
        while (true) {
            long time = System.nanoTime();
            float delta_time = (time - last_time);
            update(delta_time / 60000000);
            last_time = time;
        }

    }
}