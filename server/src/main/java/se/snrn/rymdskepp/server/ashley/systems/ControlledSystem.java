package se.snrn.rymdskepp.server.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import se.snrn.rymdskepp.CommandMessage;
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
        movementComponent.velocity.x =v;
    }

    public void setTurning(float angle) {
        MovementComponent movementComponent = entities.get(0).getComponent(MovementComponent.class);
        movementComponent.rotation = angle;
    }

    public void shoot(Entity entity) {
        getEngine().getSystem(WeaponSystem.class).shoot(entity);
    }

    public void handleCommand(CommandMessage commandMessage) {
        switch (commandMessage.getCommand()) {
            case LEFT_DOWN:
                setTurning(0.005f);
                break;
            case LEFT_UP:
                setTurning(0);
                break;
            case RIGHT_DOWN:
                setTurning(-0.005f);
                break;
            case RIGHT_UP:
                setTurning(0);
                break;
            case ACCELERATE_DOWN:
                setYVelocity(0.025f);
                break;
            case ACCELERATE_UP:
                setYVelocity(0);
                break;
            case SHOOT:
                System.out.println("PEW PEW");
                break;
        }
    }
}
