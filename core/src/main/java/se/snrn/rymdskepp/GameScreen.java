package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.GUIConsole;
import se.snrn.rymdskepp.factories.BulletFactory;
import se.snrn.rymdskepp.factories.ShipFactory;
import se.snrn.rymdskepp.systems.RenderingSystem;
import se.snrn.rymdskepp.ui.PlayerStatusUI;

import java.util.HashSet;

import static se.snrn.rymdskepp.Shared.HEIGHT;
import static se.snrn.rymdskepp.Shared.WIDTH;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {

    private FillViewport viewport;
    private OrthographicCamera camera;
    private Skin skin;
    private Stage stage;
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
    private InputMultiplexer multiplexer;
    private GUIConsole console;

    public GameScreen(Rymdskepp rymdskepp, Batch batch, Engine engine, WebSocketClient webSocketClient) {
        this.rymdskepp = rymdskepp;
        shipFactory = new ShipFactory();
        bulletFactory = new BulletFactory();
        this.batch = batch;
        this.webSocketClient = webSocketClient;

        camera = new OrthographicCamera();
        viewport = new FillViewport(WIDTH, HEIGHT);
        viewport.setCamera(camera);
        this.stage = new Stage(viewport);

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




        console = new GUIConsole(true);
        MyCommandExecutor myCommandExecutor = new MyCommandExecutor(webSocketClient);



        console.setCommandExecutor(myCommandExecutor);

        console.setSizePercent(100, 50);


        multiplexer = new InputMultiplexer();

        myInputProcessor = new MyInputProcessor(webSocketClient,console);

        multiplexer.addProcessor(myInputProcessor);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(console.getInputProcessor());


        skin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));


        stage.addActor(new PlayerStatusUI(skin));

        stage.setDebugAll(true);



    }

    public GUIConsole getConsole() {
        return console;
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
        Gdx.input.setInputProcessor(multiplexer);
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


        stage.act(delta);
        stage.draw();

        console.draw();


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