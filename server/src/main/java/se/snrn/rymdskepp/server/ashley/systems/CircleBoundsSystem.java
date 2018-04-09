package se.snrn.rymdskepp.server.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.server.ashley.Mappers;
import se.snrn.rymdskepp.server.ashley.components.CircleBoundsComponent;
import se.snrn.rymdskepp.server.ashley.components.TransformComponent;


public class CircleBoundsSystem extends IteratingSystem {

    public CircleBoundsSystem() {
        super(Family.all(CircleBoundsComponent.class, TransformComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos = Mappers.transformMapper.get(entity);
        CircleBoundsComponent bounds = Mappers.circleBoundsComponentMapper.get(entity);

        bounds.circle.setPosition(pos.pos.x, pos.pos.y);
    }
}