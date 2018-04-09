package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public class BoundsComponent implements Component, Poolable {
    public Rectangle bounds;

    public BoundsComponent() {
       bounds = new Rectangle();
    }

    @Override
    public void reset() {
        bounds = new Rectangle();
    }
}
