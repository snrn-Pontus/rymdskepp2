package se.snrn.rymdskepp.factories;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;

public class LightFactory {
    private RayHandler rayHandler;

    public LightFactory(RayHandler rayHandler) {
        this.rayHandler = rayHandler;
    }

    public PointLight createLight(Color color,float distance){
        return new PointLight(rayHandler,24, color,distance,5,5);
    }
    public PointLight createPointLight(Color color,float distance){
        return new PointLight(rayHandler,24, color,distance,5,5);
    }
    public ConeLight createConeLight(Color color,float distance){
        return new ConeLight(rayHandler,24, color,distance,5,5,0,30);
    }

}
