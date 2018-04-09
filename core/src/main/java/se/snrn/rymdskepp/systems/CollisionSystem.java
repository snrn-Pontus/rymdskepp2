package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.*;

public class CollisionSystem extends EntitySystem {
    private Engine engine;
    private ImmutableArray<Entity> bullets;
    private ImmutableArray<Entity> asteroids;


    public CollisionSystem() {

    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;

        bullets = engine.getEntitiesFor(Family.all(BulletComponent.class, TransformComponent.class, CircleBoundsComponent.class).get());
        asteroids = engine.getEntitiesFor(Family.all(AsteroidComponent.class, TransformComponent.class, CircleBoundsComponent.class).get());

    }

    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < bullets.size(); ++i) {
            Entity bullet = bullets.get(i);
            CircleBoundsComponent bulletBounds = Mappers.circleBoundsComponentMapper.get(bullet);

            for (int j = 0; j < asteroids.size(); ++j) {
                Entity asteroid = asteroids.get(j);

                CircleBoundsComponent asteroidBounds = Mappers.circleBoundsComponentMapper.get(asteroid);

                if (asteroidBounds.circle.overlaps(bulletBounds.circle)) {
                    System.out.println("Hit!");
                    engine.getSystem(AsteroidSystem.class).hit(asteroid, bullet);
                    engine.removeEntity(asteroid);
                    engine.removeEntity(bullet);
                }
            }

        }


    }
}