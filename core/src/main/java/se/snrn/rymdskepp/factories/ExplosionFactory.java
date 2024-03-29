package se.snrn.rymdskepp.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import se.snrn.rymdskepp.Coordinates;
import se.snrn.rymdskepp.Resources;
import se.snrn.rymdskepp.components.*;

import static com.badlogic.gdx.math.MathUtils.radDeg;

public class ExplosionFactory {
    private static LightFactory lightFactory;

    public ExplosionFactory(LightFactory lightFactory) {

        ExplosionFactory.lightFactory = lightFactory;
    }

    public void createExplosion(Engine engine, Coordinates coordinates){


        for (int i = 0; i < 4; i++) {
            Entity wreckage = engine.createEntity();

            TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
            transformComponent.pos.set(coordinates.getX(),coordinates.getY(),0);

            wreckage.add(transformComponent);

            TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
            textureComponent.region = Resources.getTextureRegion("wreck");

            wreckage.add(textureComponent);

            MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
            movementComponent.velocity.set(MathUtils.random(0.1f,1.5f),MathUtils.random(0.1f,1.5f)).setAngle((MathUtils.random(30,60) *i+1)*radDeg);
            movementComponent.rotation = MathUtils.random(0.1f,1.5f);

            ExpiringComponent expiringComponent = engine.createComponent(ExpiringComponent.class);

            expiringComponent.setTimeToLive(3f);

            wreckage.add(expiringComponent);
            wreckage.add(movementComponent);

            LightComponent wreckageLight = engine.createComponent(LightComponent.class);

            wreckageLight.setLight(lightFactory.createLight(Color.RED, 1));
            wreckage.add(wreckageLight);

            engine.addEntity(wreckage);

        }

        Entity flash = engine.createEntity();

        LightComponent lightComponent = engine.createComponent(LightComponent.class);
        lightComponent.setLight(lightFactory.createLight(Color.WHITE,10));
        flash.add(lightComponent);

        ExpiringComponent flashExpiringComponent = engine.createComponent(ExpiringComponent.class);

        flashExpiringComponent.setTimeToLive(0.5f);


        flash.add(flashExpiringComponent);
        Entity particles = engine.createEntity();

        TransformComponent particlesTransformComponent = engine.createComponent(TransformComponent.class);
        particlesTransformComponent.pos.set(coordinates.getX(),coordinates.getY(),0);


        particles.add(particlesTransformComponent);
        particles.add(ParticleEmitterComponent.create(engine)
                .setParticleImage(Resources.getTextureRegion("bullet"))
                .setParticleMinMaxScale(0.02f, 0.3f)
                .setSpawnType(ParticleSpawnType.FROM_CENTER)
                .setSpawnRate(30f)
                .setZIndex(10f)
                .setParticleLifespans(0.2f, 0.5f)
                .setShouldFade(true)
                .setShouldLoop(true)
                .setAngleRange(0,360)
                .setSpawnRange(1f, 1f)
                .setSpeed(5f, 10f));
        ExpiringComponent expiringComponent = engine.createComponent(ExpiringComponent.class);
        expiringComponent.setTimeToLive(0.2f);
        particles.add(expiringComponent);
        engine.addEntity(particles);
        engine.addEntity(flash);


    }
}
