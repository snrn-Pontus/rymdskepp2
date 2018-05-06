package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.websocket.data.WebSocketException;
import com.strongjoshua.console.CommandExecutor;
import com.strongjoshua.console.Console;
import com.strongjoshua.console.GUIConsole;
import se.snrn.rymdskepp.ships.JsonShipFactory;
import se.snrn.rymdskepp.ui.NetworkStatusUI;
import se.snrn.rymdskepp.ui.SetupUI;

import static se.snrn.rymdskepp.ConnectionStatus.CONNECTED;
import static se.snrn.rymdskepp.ConnectionStatus.ERROR;
import static se.snrn.rymdskepp.Shared.HEIGHT;
import static se.snrn.rymdskepp.Shared.WIDTH;

public class LobbyScreen extends ScreenAdapter {
    private GUIConsole console;
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
    public static JsonShipFactory jsonShipFactory;
    private InputMultiplexer multiplexer;

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

        jsonShipFactory = new JsonShipFactory();


        setupUI = new SetupUI("", skin, this);

        networkStatusUI = new NetworkStatusUI(skin);
        setupUI.row();
        setupUI.add(networkStatusUI).colspan(4);

        stage.addActor(setupUI);

        stage.setDebugAll(true);


        console = new GUIConsole(true);
        console.setCommandExecutor(new CommandExecutor() {
            @Override
            protected void setConsole(Console c) {
                super.setConsole(c);
            }
        });

        console.setSizePercent(100, 50);


        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.TAB) {
                    console.setVisible(!console.isVisible());
                    stage.unfocusAll();
                }
                return false;
            }
        });


        console.getInputProcessor().keyDown(Input.Keys.TAB);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(console.getInputProcessor());

        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET)
                .url("http://"+Rymdskepp.url+":8080"+"/players").build();

        Net.HttpResponseListener httpResponseListener = new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                JsonReader jsonReader = new JsonReader();
                JsonValue jsonValue = jsonReader.parse(httpResponse.getResultAsString());
                int players = jsonValue.getInt("players");
                networkStatusUI.updateServerPlayers(players);
            }

            @Override
            public void failed(Throwable t) {
                System.out.println(t.toString());
            }

            @Override
            public void cancelled() {

            }
        };

        Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);


    }

    public JsonShipFactory getJsonShipFactory() {
        return jsonShipFactory;
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

        console.draw();

    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(multiplexer);
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

    public void join(String name, int selectedShip) {
        webSocketClient.joinGame(name, selectedShip);
        rymdskepp.joinGame();
    }
}
