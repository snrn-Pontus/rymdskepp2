package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.czyzby.websocket.WebSockets;
import se.snrn.rymdskepp.ConnectionStatus;
import se.snrn.rymdskepp.LobbyScreen;
import se.snrn.rymdskepp.Rymdskepp;

import static se.snrn.rymdskepp.Shared.DEFAULT_NAME;
import static se.snrn.rymdskepp.Shared.DEFAULT_PORT;

public class SetupUI extends Window {
    private TextButton connectButton;
    private TextButton joinButton;
    private ConnectionStatus connectionStatus;

    public SetupUI(String title, Skin skin, LobbyScreen lobbyScreen) {
        super(title, skin);
        TextField nameField = new TextField(DEFAULT_NAME + "_" + MathUtils.random(), skin) {
            @Override
            public float getMinWidth() {
                return 300;
            }
        };

        setFillParent(true);
        add(new Label("Rymdspel", skin, "title")).colspan(4).pad(10);
        row();

        Label serverLabel = new Label("Address", skin);
        add(serverLabel);
        Label portLabel = new Label("Port", skin);
        add(portLabel);
        row();


        String prefilledUrl = "";
        if (Rymdskepp.url != null && Rymdskepp.url.length() > 0) {
            prefilledUrl = Rymdskepp.url;
            prefilledUrl = prefilledUrl.replace("http://", "");
            if (prefilledUrl.charAt(prefilledUrl.length() - 1) == '/') {
                prefilledUrl = prefilledUrl.substring(0, prefilledUrl.length() - 2);
            }
        }


        TextField serverAddress = new TextField(prefilledUrl, skin) {
            @Override
            public float getMinWidth() {
                return 300;
            }
        };
        add(serverAddress).padBottom(10);
        TextField serverPort = new TextField(DEFAULT_PORT + "", skin) {
            @Override
            public float getMinWidth() {
                return 300;
            }
        };
        serverPort.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        serverPort.setMaxLength(8);
        add(serverPort).padBottom(10);
        connectButton = new TextButton("Connect", skin) {
            @Override
            public boolean isDisabled() {
                return serverAddress.getText().length() == 0 ||
                        serverPort.getText().length() == 0 ||
                        nameField.getText().length() == 0 ||
                        !WebSockets.isPortValid(Integer.valueOf(serverPort.getText()));
            }
        };

        row();

        Label nameLabel = new Label("Name", skin);

        add(nameLabel);

        row();

        add(nameField).padBottom(10);
        row();
        ShipSelector shipSelector = new ShipSelector(skin,lobbyScreen.getJsonShipFactory());
        add(shipSelector);

        connectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (!connectButton.isDisabled()) {
                    lobbyScreen.connect(serverAddress.getText(), Integer.valueOf(serverPort.getText()));
                }
            }
        });

        joinButton = new TextButton("Join server", skin) {
            @Override
            public boolean isDisabled() {
                return connectionStatus != ConnectionStatus.CONNECTED;
            }
        };
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (!connectButton.isDisabled()) {
                    lobbyScreen.join(nameField.getText(),shipSelector.getSelectedShip().getId());
                }
            }
        });

        add(connectButton).padLeft(10).padRight(10);
        add(joinButton).padLeft(10).padRight(10);
    }

    public void setConnectionStatus(ConnectionStatus connectionStatus) {
        this.connectionStatus = connectionStatus;
    }
}
