package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.SharedMappers;
import se.snrn.rymdskepp.components.ParallaxComponent;
import se.snrn.rymdskepp.components.TransformComponent;


public class ParallaxSystem extends IteratingSystem {
    private Vector2 tmp = new Vector2();


    public ParallaxSystem() {
        super(Family.all(TransformComponent.class, ParallaxComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = SharedMappers.transformMapper.get(entity);
        ParallaxComponent parallaxComponent = Mappers.parallaxMapper.get(entity);


        tmp.set(1,0).scl(deltaTime).scl(transformComponent.scale.x);



        transformComponent.pos.add(tmp.x, tmp.y, 0.0f);

    }
}