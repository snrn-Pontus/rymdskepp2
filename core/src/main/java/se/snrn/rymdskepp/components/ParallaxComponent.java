package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class ParallaxComponent implements Component,Pool.Poolable {

    private int layer;

    public ParallaxComponent() {
    }

    public ParallaxComponent(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    @Override
    public void reset() {
        layer = 0;
    }
}
