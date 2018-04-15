package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.websocket.data.WebSocketException;
import se.snrn.rymdskepp.ui.NetworkStatusUI;
import se.snrn.rymdskepp.ui.SetupUI;

import static se.snrn.rymdskepp.ConnectionStatus.CONNECTED;
import static se.snrn.rymdskepp.ConnectionStatus.ERROR;
import static se.snrn.rymdskepp.Shared.HEIGHT;
import static se.snrn.rymdskepp.Shared.WIDTH;

public class LobbyScreen extends ScreenAdapter {
    private WebSocketClient webSocketClient;
    private Viewport viewport;
    private Stage stage;
    private Batch batch;
    private Rymdskepp rymdskepp;
    private Engine engine;
    private Camera camera;
    private Skin skin;
    private SetupUI setupUI;
    private NetworkStatusUI networkStatusUI;
    private ConnectionStatus connectionStatus;


    public LobbyScreen(Batch batch, Rymdskepp rymdskepp, Engine engine) {
        super();
        this.batch = batch;
        this.rymdskepp = rymdskepp;
        this.engine = engine;
        camera = new OrthographicCamera();
        viewport = new FillViewport(WIDTH, HEIGHT);
        viewport.setCamera(camera);
        this.stage = new Stage(viewport);

        skin = new Skin(Gdx.files.internal("skin/quantum-horizon-ui.json"));


        setupUI = new SetupUI("", skin, this);

        networkStatusUI = new NetworkStatusUI(skin);
        setupUI.row();
        setupUI.add(networkStatusUI).colspan(4);

        stage.addActor(setupUI);

        stage.setDebugAll(true);
    }

    public void connect(String serverAddress, int serverPort) {
        webSocketClient = new WebSocketClient(engine, rymdskepp, serverAddress, serverPort);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    public WebSocketClient getWebSocketClient() {
        return webSocketClient;
    }

    public void connectionError(WebSocketException e) {
        connectionStatus = ERROR;
        networkStatusUI.updateNetworkStatus(e.getLocalizedMessage());
    }

    public void connectionEstablished() {
        connectionStatus = CONNECTED;
        networkStatusUI.updateNetworkStatus("Connected");
        setupUI.setConnectionStatus(connectionStatus);

    }

    public void join(String name) {
        webSocketClient.joinGame(name);
        rymdskepp.joinGame();
    }
}
