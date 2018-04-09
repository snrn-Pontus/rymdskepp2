package se.snrn.rymdskepp.server.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import org.java_websocket.server.WebSocketServer;
import se.snrn.rymdskepp.server.SimpleServer;
import se.snrn.rymdskepp.server.ashley.systems.*;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class AshleyStarter implements Runnable{

    private Engine engine;
    private SimpleServer server;

    public AshleyStarter(SimpleServer server) {
        this.server = server;

        engine = new Engine();



        engine = new PooledEngine();
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BulletSystem());

        ControlledSystem controlledSystem = new ControlledSystem();
        engine.addSystem(controlledSystem);

        engine.addSystem(new WrapAroundSystem());

        engine.addSystem(new BoundsSystem());

        engine.addSystem(new CircleBoundsSystem());

        engine.addSystem(new CollisionSystem());

        engine.addSystem(new AsteroidSystem());

        engine.addSystem(new NetworkSystem(server));

        engine.addSystem(new WeaponSystem());


        AsteroidFactory.random(engine);
        AsteroidFactory.random(engine);
        AsteroidFactory.random(engine);
        AsteroidFactory.random(engine);
        AsteroidFactory.random(engine);
        AsteroidFactory.random(engine);

        Entity ship = ShipFactory.createNewShip(engine);

        System.out.println(engine);

    }

    private void update(float deltaTime) {
        System.out.println("update!");
        engine.update(deltaTime);
    }

    @Override
    public void run() {
        while(true){
            update(0.1f);
        }
    }
}