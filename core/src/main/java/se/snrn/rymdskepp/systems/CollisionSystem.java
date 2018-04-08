package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import se.snrn.rymdskepp.components.*;

public class CollisionSystem extends EntitySystem {
    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<BoundsComponent> bm;
    private ComponentMapper<MovementComponent> mm;

    private Engine engine;
    private ImmutableArray<Entity> bullets;
    private ImmutableArray<Entity> asteroids;


    public CollisionSystem() {

        bm = ComponentMapper.getFor(BoundsComponent.class);
        mm = ComponentMapper.getFor(MovementComponent.class);
        tm = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;

        bullets = engine.getEntitiesFor(Family.all(BulletComponent.class,TransformComponent.class, BoundsComponent.class).get());
        asteroids = engine.getEntitiesFor(Family.all(AsteroidComponent.class, TransformComponent.class, BoundsComponent.class).get());

    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < bullets.size(); ++i) {
            Entity bullet = bullets.get(i);
            BoundsComponent bulletBounds = bm.get(bullet);

            for (int j = 0; j < asteroids.size(); ++j) {
                Entity asteroid = asteroids.get(j);

                BoundsComponent asteroidBounds = bm.get(asteroid);

                if (asteroidBounds.bounds.overlaps(bulletBounds.bounds)) {
                    engine.getSystem(AsteroidSystem.class).hit(asteroid,bullet);
                    engine.removeEntity(asteroid);
                    engine.removeEntity(bullet);
                }
            }

        }


    }
}