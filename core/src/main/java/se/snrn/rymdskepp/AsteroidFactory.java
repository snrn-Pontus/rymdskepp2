package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import se.snrn.rymdskepp.components.*;
import se.snrn.rymdskepp.systems.RenderingSystem;

public class AsteroidFactory {

    public static Vector3 getRandomLocation(){
        Vector3 vector3 = new Vector3();
        float x = MathUtils.random(RenderingSystem.FRUSTUM_WIDTH);
        float y = MathUtils.random(RenderingSystem.FRUSTUM_HEIGHT);
        vector3.set(x,y,0.0f);
        return vector3;
    }

    public static void random(Engine engine){
        Entity entity = engine.createEntity();
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.pos.set(getRandomLocation());
        transformComponent.rotation = MathUtils.degRad * 90;
        entity.add(transformComponent);
        entity.add(new TextureComponent(new TextureRegion(new Texture("big.png"))));
        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.velocity.setToRandomDirection();
        entity.add(movementComponent);
        AsteroidComponent asteroidComponent = engine.createComponent(AsteroidComponent.class);
        asteroidComponent.size = Size.BIG;
        entity.add(asteroidComponent);        entity.add(new WrapAroundComponent());
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        boundsComponent.bounds.width = 1;
        boundsComponent.bounds.height = 1;
        entity.add(boundsComponent);
        engine.addEntity(entity);
    }
    public static void medium(Engine engine, Entity destroyed, boolean left){
        Entity entity = engine.createEntity();
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        Vector3 pos = destroyed.getComponent(TransformComponent.class).pos;
        transformComponent.pos.set(pos);
        transformComponent.rotation = MathUtils.degRad * 90;
        entity.add(transformComponent);
        entity.add(new TextureComponent(new TextureRegion(new Texture("medium.png"))));
        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.velocity.setToRandomDirection();
        entity.add(movementComponent);
        AsteroidComponent asteroidComponent = engine.createComponent(AsteroidComponent.class);
        asteroidComponent.size = Size.MEDIUM;
        entity.add(asteroidComponent);        entity.add(new WrapAroundComponent());
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        boundsComponent.bounds.width = 0.5f;
        boundsComponent.bounds.height = 0.5f;
        entity.add(boundsComponent);
        engine.addEntity(entity);
    }

    public static void small(Engine engine, Entity destroyed, boolean left) {
        Entity entity = engine.createEntity();
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        Vector3 pos = destroyed.getComponent(TransformComponent.class).pos;
        transformComponent.pos.set(pos);
        transformComponent.rotation = MathUtils.degRad * 90;
        entity.add(transformComponent);
        entity.add(new TextureComponent(new TextureRegion(new Texture("small.png"))));
        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.velocity.setToRandomDirection();
        entity.add(movementComponent);
        AsteroidComponent asteroidComponent = engine.createComponent(AsteroidComponent.class);
        asteroidComponent.size = Size.SMALL;
        entity.add(asteroidComponent);

        entity.add(new WrapAroundComponent());
        BoundsComponent boundsComponent = engine.createComponent(BoundsComponent.class);
        boundsComponent.bounds.width = 0.25f;
        boundsComponent.bounds.height = 0.25f;
        entity.add(boundsComponent);
        engine.addEntity(entity);
    }
}
