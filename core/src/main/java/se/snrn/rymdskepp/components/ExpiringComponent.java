package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ExpiringComponent implements Component, Pool.Poolable {
    private float timeAlive = 0;
    private float timeToLive = 3;

    public ExpiringComponent() {

    }

    public boolean shouldDie() {
        return this.timeAlive >= timeToLive;
    }

    public float getTimeAlive() {
        return timeAlive;
    }

    public void setTimeAlive(float timeAlive) {
        this.timeAlive = timeAlive;
    }

    public float getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(float timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Override
    public void reset() {
        timeAlive = 0;
        timeToLive = 3;
    }
}
