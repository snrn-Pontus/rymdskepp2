package se.snrn.rymdskepp.server.ashley.components;

import com.badlogic.ashley.core.Component;
import se.snrn.rymdskepp.ObjectType;

import static com.badlogic.gdx.utils.Pool.Poolable;

public class NetworkedComponent implements Component, Poolable {

    public long id;
    public ObjectType type;

    @Override
    public void reset() {
        id = 0;
        type = null;
    }
}
