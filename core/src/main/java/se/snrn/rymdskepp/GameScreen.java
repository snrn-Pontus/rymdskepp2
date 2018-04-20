package se.snrn.rymdskepp;

import box2dLight.RayHandler;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.strongjoshua.console.GUIConsole;
import se.snrn.rymdskepp.factories.*;
import se.snrn.rymdskepp.systems.*;
import se.snrn.rymdskepp.ui.PlayerStatusUI;

import java.util.HashSet;

import static se.snrn.rymdskepp.Shared.*;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {

    private FillViewport viewport;
    private Viewport uiViewport;
    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private Skin skin;
    private Stage stage;
    private se.snrn.rymdskepp.factories.ShipFactory shipFactory;
    private SoundSignal soundSignal;
    private SoundListener soundListener;
    private Batch batch;
    private Batch uiBatch;
    private Engine engine;
    private MyInputProcessor myInputProcessor;
    private Rymdskepp rymdskepp;
    private WebSocketClient webSocketClient;
    private BulletFactory bulletFactory;
    private HashSet<Long> spawnedBullets;
    private InputMultiplexer multiplexer;
    private GUIConsole console;
    private LightFactory lightFactory;


    public GameScreen(Rymdskepp rymdskepp, Batch batch, Engine engine, WebSocketClient webSocketClient) {
        this.rymdskepp = rymdskepp;
        shipFactory = new ShipFactory();
        bulletFactory = new BulletFactory();
        this.batch = batch;
        this.webSocketClient = webSocketClient;


        uiBatch = new SpriteBatch();

        camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        viewport = new FillViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        viewport.setCamera(camera);
        uiCamera = new OrthographicCamera(WIDTH, HEIGHT);
        uiViewport = new FillViewport(WIDTH, HEIGHT);
        uiViewport.setCamera(uiCamera);
        camera.position.set(viewport.getScreenWidth() / 2, viewport.getScreenHeight() / 2, 0);
        uiCamera.position.set(uiViewport.getScreenWidth() / 2, uiViewport.getScreenHeight() / 2, 0);
        uiBatch.setProjectionMatrix(uiCamera.combined);
        this.stage = new Stage(uiViewport, uiBatch);

        this.engine = engine;

        engine.addSystem(new ParallaxSystem());

        engine.addSystem(new BackgroundSystem(batch, camera));

        engine.addSystem(new RenderingSystem(batch, camera));

        engine.addSystem(new WrapAroundSystem());

        engine.addSystem(new MovementSystem());

        engine.addSystem(new ParticleSystem());

//        engine.addSystem(new DebugRenderingSystem(camera));

        spawnedBullets = new HashSet<>();


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

        myInputProcessor = new MyInputProcessor(webSocketClient, console);

        multiplexer.addProcessor(myInputProcessor);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(console.getInputProcessor());


        skin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));


        PlayerStatusUI playerStatusUI = new PlayerStatusUI(skin);
        playerStatusUI.setSize(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);

//        playerStatusUI.setScale(PIXELS_TO_METRES);
        stage.addActor(playerStatusUI);


        stage.setDebugAll(true);


        World world = new World(new Vector2(), true);

        RayHandler rayHandler = new RayHandler(world);


        lightFactory = new LightFactory(rayHandler);

        StarFactory.createStar(engine, lightFactory);
        NebulaFactory.createNebula(engine);

        engine.addSystem(new Box2DLightsSystem(world, rayHandler, camera, batch));


    }

    public GUIConsole getConsole() {
        return console;
    }

    public void connectionError() {
        rymdskepp.disconnected();
    }

    public void spawnShip(Engine engine, long id, String name, WebSocketClient webSocketClient, int shipType) {
        shipFactory.createShip(engine, id, name, webSocketClient, shipType, lightFactory);
    }

    private void spawnBullet(NetworkObject networkObject) {
        Entity bullet = bulletFactory.createNewBullet(networkObject, engine,lightFactory);
        engine.addEntity(bullet);
    }

    public HashSet<Long> getSpawnedBullets() {
        return spawnedBullets;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
        viewport.apply(true);
        uiViewport.apply(true);
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


//        viewport.apply();
        engine.update(delta);

        System.out.println(engine.getEntities().size());

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