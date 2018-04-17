package se.snrn.rymdskepp.server.components;

import com.badlogic.ashley.core.Component;

import static com.badlogic.gdx.utils.Pool.Poolable;

public class BulletComponent implements Component, Poolable {

    public static final float TIME_TO_LIVE = 1.5f;
    private float deltaTime;
    private long id;
    private String owner;

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
        owner = "";
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
}
