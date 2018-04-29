package se.snrn.rymdskepp.components;

import box2dLight.ChainLight;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ChainLightComponent implements Component, Poolable {

    private ChainLight chainLight;
    private ChainLight secondChainLight;

    public ChainLightComponent(ChainLight chainLight) {
        this.chainLight = chainLight;
    }

    public ChainLightComponent() {

    }

    public ChainLight getChainLight() {
        return chainLight;
    }

    public void setChainLight(ChainLight chainLight) {
        this.chainLight = chainLight;
    }

    @Override
    public void reset() {

    }

    public void setLightPosition(Vector2 vector2) {
        chainLight.setPosition(vector2);
        secondChainLight.setPosition(vector2);
    }


    public void update() {
        chainLight.updateChain();
        secondChainLight.updateChain();
    }

    public void setSecondChainLight(ChainLight secondChainLight) {
        this.secondChainLight = secondChainLight;
    }

    public ChainLight getSecondChainLight() {
        return secondChainLight;
    }
}
