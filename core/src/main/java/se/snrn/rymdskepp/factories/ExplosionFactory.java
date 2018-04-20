package se.snrn.rymdskepp.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.*;

import static com.badlogic.gdx.math.MathUtils.radDeg;

public class ExplosionFactory {
    public ExplosionFactory() {

    }

    public static void createExplosion(Engine engine,Entity origin,LightFactory lightFactory){
        for (int i = 0; i < 4; i++) {
            Entity wreckage = engine.createEntity();
            TransformComponent originTransformComponent = Mappers.transformMapper.get(origin);

            TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
            transformComponent.pos.set(5,5,0);
            transformComponent.scale.set(originTransformComponent.scale.cpy());

            wreckage.add(transformComponent);

            TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
            textureComponent.region = new TextureRegion(new Texture(Gdx.files.internal("wreck.png")));

            wreckage.add(textureComponent);

            MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
            movementComponent.velocity.set(MathUtils.random(0.1f,1.5f),MathUtils.random(0.1f,1.5f)).setAngle((MathUtils.random(30,60) *i+1)*radDeg);
            movementComponent.rotation = MathUtils.random(0.1f,1.5f);

            ExpiringComponent expiringComponent = engine.createComponent(ExpiringComponent.class);

            expiringComponent.setTimeToLive(3f);

            wreckage.add(expiringComponent);
            wreckage.add(movementComponent);

            Entity flash = engine.createEntity();

            LightComponent lightComponent = engine.createComponent(LightComponent.class);
            lightComponent.setLight(lightFactory.createLight(Color.WHITE,10));
            flash.add(lightComponent);

            ExpiringComponent flashExpiringComponent = engine.createComponent(ExpiringComponent.class);

            flashExpiringComponent.setTimeToLive(0.2f);

            flash.add(flashExpiringComponent);

            engine.addEntity(flash);

            System.out.println(wreckage);
            engine.addEntity(wreckage);
        }

    }
}
