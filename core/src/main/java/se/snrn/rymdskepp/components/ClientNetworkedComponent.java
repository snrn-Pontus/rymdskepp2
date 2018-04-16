package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import se.snrn.rymdskepp.WebSocketClient;

import static com.badlogic.gdx.utils.Pool.Poolable;

public class ClientNetworkedComponent implements Component, Poolable {

    public WebSocketClient webSocketClient;
    public long id;

    @Override
    public void reset() {
        id = 0;
        webSocketClient = null;
    }
}
