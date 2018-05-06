package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class NetworkStatusUI extends Table {
    private Label playerNumber;
    private Label networkStatusLabel;
    private Label windowParams;

    public NetworkStatusUI(Skin skin) {
        super(skin);
        networkStatusLabel = new Label("", skin);
        add(networkStatusLabel);
        windowParams = new Label("", skin);
        add(windowParams);
        playerNumber = new Label("",skin);
        add(playerNumber);

    }

    public void updateNetworkStatus(String networkStatus){
        networkStatusLabel.setText(networkStatus);
    }

    public void updateServerPlayers(int serverPlayers) {
      playerNumber.setText(serverPlayers+" players on the server.");
    }
}
