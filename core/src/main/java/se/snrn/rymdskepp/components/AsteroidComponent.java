package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import se.snrn.rymdskepp.Size;


public class AsteroidComponent implements Component,Poolable {
    public Size size;

    @Override
    public void reset() {
        size = null;
    }
}
