package se.snrn.rymdskepp.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import se.snrn.rymdskepp.components.*;

import static se.snrn.rymdskepp.Shared.FRUSTUM_HEIGHT;
import static se.snrn.rymdskepp.Shared.FRUSTUM_WIDTH;

public class StarFactory {


    public static Vector2 getRandomPosition() {
        float randomX = MathUtils.random(0, FRUSTUM_WIDTH);
        float randomY = MathUtils.random(0, FRUSTUM_HEIGHT);

        return new Vector2(randomX, randomY);
    }


    public static void createStar(Engine engine,LightFactory lightFactory) {
        for (int i = 0; i < 40; i++) {
            Entity star = engine.createEntity();
            int layer = MathUtils.random(2, 5);
            TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
            textureComponent.region = new TextureRegion(new Texture(Gdx.files.internal("star.png")));
            star.add(textureComponent);
            TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
            Vector2 randomPosition = getRandomPosition();
            transformComponent.pos.set(randomPosition.x, randomPosition.y, layer);
            transformComponent.rotation = 0f;
            transformComponent.scale.set(layer/5f,layer/5f);
            ParallaxComponent parallaxComponent = engine.createComponent(ParallaxComponent.class);
            parallaxComponent.setLayer(layer);
            star.add(parallaxComponent);
            star.add(transformComponent);
            WrapAroundComponent wrapAroundComponent = engine.createComponent(WrapAroundComponent.class);
            star.add(wrapAroundComponent);
            LightComponent lightComponent = engine.createComponent(LightComponent.class);
            lightComponent.setLight(lightFactory.createLight(Color.WHITE,layer/2.5f));
            lightComponent.setLightPosition(transformComponent.pos.x,transformComponent.pos.y);
            star.add(lightComponent);
            engine.addEntity(star);
        }
    }
}
