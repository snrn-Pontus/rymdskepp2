package se.snrn.rymdskepp.components;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ConeLightComponent implements Component, Poolable {

    private ConeLight coneLight;
    private PointLight pointLight;

    public PointLight getPointLight() {
        return pointLight;
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    public ConeLightComponent() {
    }

    public ConeLightComponent(ConeLight coneLight) {
        this.coneLight = coneLight;
    }

    public ConeLight getConeLight() {
        return coneLight;
    }

    public void setLight(ConeLight coneLight) {
        this.coneLight = coneLight;
    }

    public void setLightPosition(Vector2 pos){
        if(coneLight != null){
            coneLight.setPosition(pos);
        }
        if(pointLight != null){
            pointLight.setPosition(pos);
        }
    }
    public void setLightPosition(float x,float y ){
        if(coneLight != null){
            coneLight.setPosition(x,y);
        }
        if(pointLight != null){
            pointLight.setPosition(x,y);
        }
    }

    @Override
    public void reset() {
        coneLight.remove();
        coneLight = null;
        pointLight.remove();
        pointLight = null;
    }

    public void setDirection(float v) {
        coneLight.setDirection(v);
    }

    public void update() {
        coneLight.update();
        pointLight.update();
    }
}
