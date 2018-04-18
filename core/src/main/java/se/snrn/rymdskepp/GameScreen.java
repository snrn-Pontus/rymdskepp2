package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import se.snrn.rymdskepp.factories.BulletFactory;
import se.snrn.rymdskepp.factories.ShipFactory;
import se.snrn.rymdskepp.systems.RenderingSystem;

import java.util.HashSet;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {

    private se.snrn.rymdskepp.factories.ShipFactory shipFactory;
    private SoundSignal soundSignal;
    private SoundListener soundListener;
    private Batch batch;
    private Engine engine;
    MyInputProcessor myInputProcessor;
    private Rymdskepp rymdskepp;
    private WebSocketClient webSocketClient;
    private se.snrn.rymdskepp.factories.BulletFactory bulletFactory;
    private HashSet<Long> spawnedBullets;

    public GameScreen(Rymdskepp rymdskepp, Batch batch, Engine engine, WebSocketClient webSocketClient) {
        this.rymdskepp = rymdskepp;
        shipFactory = new ShipFactory();
        bulletFactory = new BulletFactory();
        this.batch = batch;
        this.webSocketClient = webSocketClient;

        this.engine = engine;

        engine.addSystem(new RenderingSystem(batch));


        spawnedBullets = new HashSet<>();
//
//
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);


//        Entity ship = shipFactory.createShip(engine,1,webSocketClient);
//        Entity otherShip = ShipFactory.createOtherShip(engine,webSocketClient);


//        soundListener = new SoundListener();
//
//        soundSignal = new SoundSignal();
//
//        soundSignal.add(soundListener);
//
//        soundSignal.dispatch(SoundEnum.EXPLODE);


        myInputProcessor = new MyInputProcessor(webSocketClient);


    }

    public void connectionError() {
        rymdskepp.disconnected();
    }

    public void spawnShip(Engine engine, long id, String name, WebSocketClient webSocketClient, int shipType) {
        shipFactory.createShip(engine, id, name, webSocketClient,shipType);
    }

    private void spawnBullet(NetworkObject networkObject) {
        Entity bullet = bulletFactory.createNewBullet(networkObject, engine);
        engine.addEntity(bullet);
    }

    public HashSet<Long> getSpawnedBullets() {
        return spawnedBullets;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(myInputProcessor);
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!rymdskepp.getBulletsToSpawn().isEmpty()) {
            spawnBullet(rymdskepp.getBulletsToSpawn().get(0));
            rymdskepp.getBulletsToSpawn().remove(0);

        }

        if (!rymdskepp.getPlayersToSpawn().isEmpty()) {
            spawnShip(engine, rymdskepp.getPlayersToSpawn().get(0).getId(), rymdskepp.getPlayersToSpawn().get(0).getName(), webSocketClient, rymdskepp.getPlayersToSpawn().get(0).getShipType());

            rymdskepp.getPlayersToSpawn().remove(0);
        }

        engine.update(delta);

        // Draw your screen here. "delta" is the time since last render in seconds.
    }


    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }

}