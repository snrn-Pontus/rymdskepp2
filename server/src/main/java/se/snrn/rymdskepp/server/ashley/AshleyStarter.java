package se.snrn.rymdskepp.server.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.HeadlessConsole;
import se.snrn.rymdskepp.server.ConsoleLogger;
import se.snrn.rymdskepp.server.GameState;
import se.snrn.rymdskepp.server.Player;
import se.snrn.rymdskepp.server.WebSocketServer;
import se.snrn.rymdskepp.server.ashley.systems.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class AshleyStarter implements Runnable {

    private ConsoleLogger consoleLogger;
    private Engine engine;
    private WebSocketServer webSocketServer;
    private GameState gameState;

    private ControlledSystem controlledSystem;

    public AshleyStarter(WebSocketServer webSocketServer, GameState gameState) {
        this.webSocketServer = webSocketServer;
        this.gameState = gameState;

        engine = new Engine();

        ShipFactory.setEngine(engine);


        engine = new PooledEngine();
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BulletSystem());

        controlledSystem = new ControlledSystem(gameState.getPlayers());
        engine.addSystem(controlledSystem);

        engine.addSystem(new WrapAroundSystem());

        engine.addSystem(new BoundsSystem());

        engine.addSystem(new CircleBoundsSystem());

        engine.addSystem(new CollisionSystem());

        engine.addSystem(new AsteroidSystem());

        engine.addSystem(new NetworkSystem(webSocketServer));

        engine.addSystem(new WeaponSystem());
//
//
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);

//        Entity ship = ShipFactory.createNewShip(engine);

//        System.out.println(engine);

        webSocketServer.setEngine(engine);

        consoleLogger = ConsoleLogger.getInstance();
    }

    private void update(float deltaTime) {
//        System.out.println("update!");
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
                consoleLogger.log("Spawned: "+player.getId());
                player.setSpawned(true);
                player.setMovementComponent(Mappers.movementMapper.get(newShip));
                controlledSystem.getPlayerHash().put(player.getId(), player.getMovementComponent());
            }
        }
    }

    @Override
    public void run() {
        long last_time = System.nanoTime();
        while (true) {

            long time = System.nanoTime();
            float delta_time = (time - last_time);
//            System.out.println(delta_time/60000000);
            update(delta_time / 60000000);
            last_time = time;
        }

    }
}