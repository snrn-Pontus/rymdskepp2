package se.snrn.rymdskepp.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.Resources;
import se.snrn.rymdskepp.components.ChainLightComponent;
import se.snrn.rymdskepp.components.LaserComponent;
import se.snrn.rymdskepp.components.TransformComponent;

public class LaserFactory {

    public static void createLaser(Engine engine, LightFactory lightFactory) {
        Entity entity = engine.createEntity();
        LaserComponent laserComponent = engine.createComponent(LaserComponent.class);
        laserComponent.setStartBase(Resources.getTextureRegion("laser_start"));
        laserComponent.setStartOverlay(Resources.getTextureRegion("laser_start_overlay"));

        laserComponent.setMiddleBase(Resources.getTextureRegion("laser_middle"));
        laserComponent.setMiddleOverlay(Resources.getTextureRegion("laser_middle_overlay"));

        entity.add(laserComponent);

        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);

        transformComponent.pos.set(5, 1, 0);
        transformComponent.rotation = 0;

        entity.add(transformComponent);

        ChainLightComponent chainLightComponent = engine.createComponent(ChainLightComponent.class);
        chainLightComponent.setChainLight(lightFactory.createChainLight(Color.GREEN, 2, 1));
        chainLightComponent.setSecondChainLight(lightFactory.createChainLight(Color.GREEN, 2, -1));

        entity.add(chainLightComponent);

        engine.addEntity(entity);
    }
}
