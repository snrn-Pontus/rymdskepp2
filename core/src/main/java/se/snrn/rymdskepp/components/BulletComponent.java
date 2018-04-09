package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;

import static com.badlogic.gdx.utils.Pool.Poolable;

public class BulletComponent implements Component, Poolable {

    public static final float TIME_TO_LIVE = 1.5f;
    private float deltaTime;

    public void addTime(float deltaTime) {
        this.deltaTime += deltaTime;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    @Override
    public void reset() {
        deltaTime = 0;
    }
}
