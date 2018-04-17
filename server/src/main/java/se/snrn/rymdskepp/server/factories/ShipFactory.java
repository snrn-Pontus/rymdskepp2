package se.snrn.rymdskepp.server.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import se.snrn.rymdskepp.ObjectType;
import se.snrn.rymdskepp.components.ControlledComponent;
import se.snrn.rymdskepp.server.components.*;
import se.snrn.rymdskepp.components.TransformComponent;

public class ShipFactory {

    private static Engine engine;

    public static Entity createNewShip(Engine engine, long id, String name) {

        Entity ship = engine.createEntity();
        ship.add(new MovementComponent(0, 0));

        NetworkedComponent networkedComponent = new NetworkedComponent();
        networkedComponent.id = id;
        networkedComponent.type = ObjectType.SHIP;
        ship.add(networkedComponent);

        TransformComponent shipTransformComponent = engine.createComponent(TransformComponent.class);
        shipTransformComponent.pos.set(5.0f, 1.0f, 0.0f);
        ship.add(shipTransformComponent);

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.velocity.set(0, 0);
        ship.add(movementComponent);
        BoundsComponent shipBoundsComponent = engine.createComponent(BoundsComponent.class);
        shipBoundsComponent.bounds.width = 1;
        shipBoundsComponent.bounds.height = 1;
        ship.add(shipBoundsComponent);
        ship.add(new WrapAroundComponent());
        ship.add(new ControlledComponent());
        ship.add(new ShipComponent());
        engine.addEntity(ship);
        return ship;
    }

    public static void setEngine(Engine engine) {
        ShipFactory.engine = engine;
    }

    public static Engine getEngine() {
        return engine;
    }
}
