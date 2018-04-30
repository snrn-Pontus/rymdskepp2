package se.snrn.rymdskepp;

import box2dLight.RayHandler;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

import java.util.ArrayList;
import java.util.HashSet;

import static se.snrn.rymdskepp.Shared.*;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {


    private Sprite sprite;
    private FillViewport viewport;
    private Viewport uiViewport;
    private OrthographicCamera camera;
    private OrthographicCamera uiCamera;
    private Skin skin;
    private Stage stage;
    private ShipFactory shipFactory;
    public static SoundSignal soundSignal;
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
    private ExplosionFactory explosionFactory;

    private ArrayList<ExplosionMessage> explosionsToSpawn;
    private ArrayList<Long> playersToRemove;
    private PlayerStatusUI playerStatusUI;

    public GameScreen(Rymdskepp rymdskepp, Batch batch, Engine engine, WebSocketClient webSocketClient) {
        this.rymdskepp = rymdskepp;
        shipFactory = new ShipFactory();
        bulletFactory = new BulletFactory();
        this.batch = batch;
        this.webSocketClient = webSocketClient;

        playersToRemove = new ArrayList<>();

        uiBatch = new SpriteBatch();

        camera = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        viewport = new FillViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        viewport.setCamera(camera);
        camera.position.set(viewport.getScreenWidth() / 2, viewport.getScreenHeight() / 2, 0);

        uiCamera = new OrthographicCamera(WIDTH, HEIGHT);
        uiViewport = new FillViewport(WIDTH, HEIGHT);
        uiViewport.setCamera(uiCamera);
        uiCamera.position.set(uiViewport.getScreenWidth() / 2, uiViewport.getScreenHeight() / 2, 0);
        uiBatch.setProjectionMatrix(uiCamera.combined);


        explosionsToSpawn = new ArrayList<>();

        this.stage = new Stage(uiViewport, uiBatch);

        this.engine = engine;

        engine.addSystem(new CameraSystem(camera));

        engine.addSystem(new ParallaxSystem());

        engine.addSystem(new BackgroundSystem(batch, camera));

        engine.addSystem(new ParticleSystem());

        engine.addSystem(new RenderingSystem(batch, camera));

        engine.addSystem(new NameTagRenderingSystem(batch, camera));

        engine.addSystem(new WrapAroundSystem());

        engine.addSystem(new MovementSystem());

        engine.addSystem(new ExpiringSystem());

        engine.addSystem(new AnimationSystem());

        engine.addSystem(new LaserRenderingSystem(batch, camera));

//        engine.addSystem(new DebugRenderingSystem(camera));

        spawnedBullets = new HashSet<>();


        soundListener = new SoundListener();

        soundSignal = new SoundSignal();

        soundSignal.add(soundListener);


        skin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));

        console = new GUIConsole(true);
        MyCommandExecutor myCommandExecutor = new MyCommandExecutor(webSocketClient);


        console.setCommandExecutor(myCommandExecutor);

        console.setSizePercent(100, 50);


        multiplexer = new InputMultiplexer();

        myInputProcessor = new MyInputProcessor(webSocketClient, console);

        multiplexer.addProcessor(myInputProcessor);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(console.getInputProcessor());


        playerStatusUI = new PlayerStatusUI(skin, rymdskepp.getPlayers());
        playerStatusUI.setSize(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);

        stage.addActor(playerStatusUI);


        stage.setDebugAll(true);


        World world = new World(new Vector2(), true);

        RayHandler rayHandler = new RayHandler(world);


        lightFactory = new LightFactory(rayHandler);


        explosionFactory = new ExplosionFactory(lightFactory);
        StarFactory.createStar(engine, lightFactory);
        NebulaFactory.createNebula(engine);

        engine.addSystem(new Box2DLightsSystem(world, rayHandler, camera, batch));
        Texture background = new Texture(Gdx.files.internal("background.png"));

        sprite = new Sprite(background);

        sprite.setSize(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);

        LaserFactory.createLaser(engine, lightFactory);
    }


    public GUIConsole getConsole() {
        return console;
    }

    public void connectionError() {
        rymdskepp.disconnected();
    }

    private Entity spawnShip(Engine engine, NewPlayerConnected newPlayerConnected, WebSocketClient webSocketClient) {
        return shipFactory.createShip(engine, newPlayerConnected, webSocketClient, lightFactory);
    }

    private void spawnBullet(NetworkObject networkObject) {
        Entity bullet = bulletFactory.createNewBullet(networkObject, engine, lightFactory);
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
//            Entity playerWhoShot = rymdskepp.getPlayers().get(rymdskepp.getBulletsToSpawn().get(0).getId());

            spawnBullet(rymdskepp.getBulletsToSpawn().get(0));
            rymdskepp.getBulletsToSpawn().remove(0);

        }
        if (!explosionsToSpawn.isEmpty()) {
            explosionFactory.createExplosion(engine, explosionsToSpawn.get(0).getCoordinates());
            explosionsToSpawn.remove(0);

        }

        if (!rymdskepp.getPlayersToSpawn().isEmpty()) {
            Entity ship = spawnShip(engine, rymdskepp.getPlayersToSpawn().get(0), webSocketClient);
            rymdskepp.getPlayers().put(rymdskepp.getPlayersToSpawn().get(0).getId(), ship);
            rymdskepp.getPlayersToSpawn().remove(0);
        }
        if (!getPlayersToRemove().isEmpty()) {

            engine.removeEntity(rymdskepp.getPlayers().get(playersToRemove.get(0)));
            getPlayersToRemove().remove(0);
        }


        batch.begin();
        sprite.draw(batch);
        batch.end();
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


    public ArrayList<ExplosionMessage> getExplosionsToSpawn() {
        return explosionsToSpawn;
    }

    public ExplosionFactory getExplosionFactory() {
        return explosionFactory;
    }

    public ArrayList<Long> getPlayersToRemove() {
        return playersToRemove;
    }

    public PlayerStatusUI getPlayerStatusUI() {
        return playerStatusUI;
    }

    public void setPlayerStatusUI(PlayerStatusUI playerStatusUI) {
        this.playerStatusUI = playerStatusUI;
    }
}