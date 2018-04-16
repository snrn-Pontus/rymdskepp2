package se.snrn.rymdskepp.server.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import se.snrn.rymdskepp.server.Size;


public class AsteroidComponent implements Component, Poolable {
    public Size size;

    @Override
    public void reset() {
        size = null;
    }
}
