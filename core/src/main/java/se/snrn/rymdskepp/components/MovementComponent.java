package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementComponent implements Component {

    public final Vector2 velocity = new Vector2();
    public final Vector2 accel = new Vector2();
    public float rotation = 0.0f;

    public MovementComponent() {
    }

    public MovementComponent (float velocityX, float velocityY) {

        velocity.set(velocityX,velocityY);
    }
}