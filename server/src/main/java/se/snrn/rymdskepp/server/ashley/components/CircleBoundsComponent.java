package se.snrn.rymdskepp.server.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CircleBoundsComponent implements Component, Poolable {
    public Circle circle;

    public CircleBoundsComponent() {
        circle = new Circle();
    }

    @Override
    public void reset() {
        circle = new Circle();
    }
}
