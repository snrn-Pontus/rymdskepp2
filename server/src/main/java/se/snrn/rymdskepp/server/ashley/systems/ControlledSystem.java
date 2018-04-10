package se.snrn.rymdskepp.server.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import se.snrn.rymdskepp.server.ashley.components.ControlledComponent;
import se.snrn.rymdskepp.server.ashley.components.MovementComponent;

public class ControlledSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;


    public ControlledSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ControlledComponent.class, MovementComponent.class).get());

    }

    public void setYVelocity(float v) {
        MovementComponent movementComponent = entities.get(0).getComponent(MovementComponent.class);
        movementComponent.accel.y = v;
    }

    public void setXVelocity(float v) {
        MovementComponent movementComponent = entities.get(0).getComponent(MovementComponent.class);
        movementComponent.velocity.x +=v;
    }

    public void setTurning(float angle) {
        MovementComponent movementComponent = entities.get(0).getComponent(MovementComponent.class);
        movementComponent.rotation = angle;
    }

    public void shoot(Entity entity) {
        getEngine().getSystem(WeaponSystem.class).shoot(entity);
    }
}
