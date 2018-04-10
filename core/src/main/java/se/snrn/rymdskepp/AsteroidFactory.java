package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import se.snrn.rymdskepp.components.*;

public class AsteroidFactory {

    static RandomXS128 locationRandom = new RandomXS128(1);
    static RandomXS128 directionRandom = new RandomXS128(2);


    public static Vector3 getRandomLocationFromSeed() {
        Vector3 vector3 = new Vector3();
        float x = locationRandom.nextFloat() * Shared.FRUSTUM_WIDTH;
        float y = locationRandom.nextFloat() * Shared.FRUSTUM_HEIGHT;
        vector3.set(x, y, 0.0f);
        return vector3;
    }
    public static Vector2 getRandomDirectionFromSeed() {
        Vector2 vector2 = new Vector2();
        float x = directionRandom.nextFloat();
        float y = directionRandom.nextFloat();
        vector2.set(x, y);
        return vector2;
    }

    public static void random(Engine engine) {
        Entity entity = engine.createEntity();
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.pos.set(getRandomLocationFromSeed());
        transformComponent.rotation = MathUtils.degRad * 90;
        entity.add(transformComponent);
        entity.add(new TextureComponent(new TextureRegion(new Texture("big.png"))));
        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.velocity.set(getRandomDirectionFromSeed());
        entity.add(movementComponent);
        engine.addEntity(entity);
    }

    public static void medium(Engine engine, Entity destroyed, boolean left) {
        Entity entity = engine.createEntity();
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        Vector3 pos = destroyed.getComponent(TransformComponent.class).pos;
        transformComponent.pos.set(pos);
        transformComponent.rotation = MathUtils.degRad * 90;
        entity.add(transformComponent);
        entity.add(new TextureComponent(new TextureRegion(new Texture("medium.png"))));
        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        Vector2 randomDirectionFromSeed = getRandomDirectionFromSeed();
        movementComponent.velocity.set(left ? randomDirectionFromSeed : randomDirectionFromSeed.rotate(180));
        entity.add(movementComponent);
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
        Vector2 randomDirectionFromSeed = getRandomDirectionFromSeed();
        movementComponent.velocity.set(left ? randomDirectionFromSeed : randomDirectionFromSeed.rotate(180));
        entity.add(movementComponent);
        engine.addEntity(entity);
    }
}
