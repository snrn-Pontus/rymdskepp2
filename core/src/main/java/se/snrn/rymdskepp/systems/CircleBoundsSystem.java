package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.CircleBoundsComponent;
import se.snrn.rymdskepp.components.TransformComponent;

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