package se.snrn.rymdskepp.server.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import se.snrn.rymdskepp.server.WebSocketServer;
import se.snrn.rymdskepp.server.ashley.systems.*;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class AshleyStarter implements Runnable{

    private Engine engine;
    private WebSocketServer webSocketServer;


    public AshleyStarter(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;

        engine = new Engine();

        ShipFactory.setEngine(engine);


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

        engine.addSystem(new NetworkSystem(webSocketServer));

        engine.addSystem(new WeaponSystem());


        AsteroidFactory.random(engine);
        AsteroidFactory.random(engine);
        AsteroidFactory.random(engine);
        AsteroidFactory.random(engine);
        AsteroidFactory.random(engine);
        AsteroidFactory.random(engine);

        Entity ship = ShipFactory.createNewShip(engine);

        System.out.println(engine);

        webSocketServer.setEngine(engine);

    }

    private void update(float deltaTime) {
//        System.out.println("update!");
        engine.update(deltaTime);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long last_time = System.nanoTime();
        while (true)
        {

                long time = System.nanoTime();
                float delta_time = (time - last_time);
//            System.out.println(delta_time/60000000);
            update(delta_time/60000000);
                last_time = time;
        }

    }
}