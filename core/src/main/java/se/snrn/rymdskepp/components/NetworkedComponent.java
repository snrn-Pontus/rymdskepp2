package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import se.snrn.rymdskepp.WebsocketManager;

import static com.badlogic.gdx.utils.Pool.Poolable;

public class NetworkedComponent implements Component, Poolable {

    public WebsocketManager websocketManager;
    public long id;

    @Override
    public void reset() {
        id = 0;
        websocketManager = null;
    }
}
