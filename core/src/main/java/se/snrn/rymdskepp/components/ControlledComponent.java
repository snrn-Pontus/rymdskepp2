package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class ControlledComponent implements Component {
    private MovementComponent owner;

    public ControlledComponent(Entity entity) {
        MovementComponent movementComponent = entity.getComponent(MovementComponent.class);
        if (movementComponent != null) {
            this.owner = movementComponent;
        }
    }

}
