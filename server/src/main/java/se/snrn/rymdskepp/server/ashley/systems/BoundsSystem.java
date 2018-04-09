package se.snrn.rymdskepp.server.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.server.ashley.Mappers;
import se.snrn.rymdskepp.server.ashley.components.BoundsComponent;
import se.snrn.rymdskepp.server.ashley.components.TransformComponent;

public class BoundsSystem extends IteratingSystem {


    public BoundsSystem() {
        super(Family.all(BoundsComponent.class, TransformComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos = Mappers.transformMapper.get(entity);
        BoundsComponent bounds = Mappers.boundsMapper.get(entity);

        bounds.bounds.x = pos.pos.x - bounds.bounds.width * 0.5f;
        bounds.bounds.y = pos.pos.y - bounds.bounds.height * 0.5f;


    }


}