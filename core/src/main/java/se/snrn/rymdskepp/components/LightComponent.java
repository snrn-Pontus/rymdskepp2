package se.snrn.rymdskepp.components;

import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class LightComponent implements Component, Poolable {

    private PointLight light;

    public LightComponent() {
    }

    public LightComponent(PointLight light) {
        this.light = light;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(PointLight light ) {
        this.light = light;
    }

    public void setLightPosition(Vector2 pos) {
        if (light != null) {
            light.setPosition(pos);
        }
    }

    public void setLightPosition(float x, float y) {
        if (light != null) {
            light.setPosition(x, y);
        }
    }

    @Override
    public void reset() {
        light.remove();
        light = null;
    }


    public void update() {
        light.update();
    }
}
