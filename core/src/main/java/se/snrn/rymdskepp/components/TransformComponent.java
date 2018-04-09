package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TransformComponent implements Component, Poolable {
    public final Vector3 pos = new Vector3();
    public final Vector2 scale = new Vector2(1.0f, 1.0f);
    public float rotation = 0.0f;

    public TransformComponent() {
    }

    @Override
    public void reset() {
        pos.setZero();
        scale.set(1.0f,1.0f);
        rotation = 0.0f;
    }
}

