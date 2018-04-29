package se.snrn.rymdskepp.factories;

import box2dLight.*;
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
    public ChainLight createChainLight(Color color, float distance, int rayDirection){
        float[] chain = new float[10];
        for (int i = 0; i < chain.length; i++) {
            chain[i] = 5;
            chain[i+1] = i+1;
            i++;
        }
        return new ChainLight(rayHandler,24, color,distance,rayDirection,chain);
    }

}
