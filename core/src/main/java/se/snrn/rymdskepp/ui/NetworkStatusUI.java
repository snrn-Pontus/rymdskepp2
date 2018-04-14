package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class NetworkStatusUI extends Table {
    private Label networkStatusLabel;

    public NetworkStatusUI(Skin skin) {
        super(skin);
        networkStatusLabel = new Label("", skin);
        add(networkStatusLabel);
    }

    public void updateNetworkStatus(String networkStatus){
        networkStatusLabel.setText(networkStatus);
    }
}
