package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import se.snrn.rymdskepp.systems.*;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class FirstScreen implements Screen {

    private SoundSignal soundSignal;
    private SoundListener soundListener;
    private Batch batch;
    private WebSocketClient webSocketClient;
    private Engine engine;
    MyInputProcessor myInputProcessor;

    public FirstScreen(Batch batch) {
        this.batch = batch;
        engine = new Engine();

        engine = new PooledEngine();
        engine.addSystem(new RenderingSystem(batch));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new NetworkSystem());

        ControlledSystem controlledSystem = new ControlledSystem();
        engine.addSystem(controlledSystem);
//
//
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);
//        AsteroidFactory.random(engine);




        Entity ship = ShipFactory.createShip(engine,webSocketClient);
//        Entity otherShip = ShipFactory.createOtherShip(engine,webSocketClient);



        soundListener = new SoundListener();

        soundSignal = new SoundSignal();

        soundSignal.add(soundListener);

        soundSignal.dispatch(SoundEnum.EXPLODE);

        webSocketClient = new WebSocketClient(engine);

        myInputProcessor = new MyInputProcessor(controlledSystem,ship,webSocketClient);


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