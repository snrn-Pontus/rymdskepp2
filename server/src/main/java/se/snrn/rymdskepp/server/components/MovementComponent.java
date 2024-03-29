package se.snrn.rymdskepp.server.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class MovementComponent implements Component, Poolable {

    public final Vector2 velocity = new Vector2();
    public final Vector2 acceleration = new Vector2();
    public float rotation = 0.0f;

    public MovementComponent() {
    }

    public MovementComponent(float velocityX, float velocityY) {
        velocity.set(velocityX, velocityY);
    }

    @Override
    public void reset() {
        velocity.setZero();
        acceleration.setZero();
        rotation = 0.0f;
    }
}