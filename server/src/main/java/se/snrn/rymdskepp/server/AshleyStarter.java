package se.snrn.rymdskepp.server;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import se.snrn.rymdskepp.server.components.ShipComponent;
import se.snrn.rymdskepp.server.factories.ShipFactory;
import se.snrn.rymdskepp.server.systems.*;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class AshleyStarter extends Game {

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

        engine.addSystem(new NetworkSystem(webSocketServer));

        engine.addSystem(new WeaponSystem());


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
                player.setMovementComponent(Mappers.movementMapper.get(newShip));
                controlledSystem.getPlayerHash().put(player.getId(), player.getMovementComponent());
                controlledSystem.getPlayerMap().put(player.getId(), newShip);
            }
        }
    }




    @Override
    public void create() {

    }

    @Override
    public void render() {
        spawnUnSpawned();
        engine.update(Gdx.graphics.getDeltaTime());
    }
}