package se.snrn.rymdskepp.server.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.NewPlayerConnected;
import se.snrn.rymdskepp.ObjectType;
import se.snrn.rymdskepp.server.ashley.components.*;

public class ShipFactory {


    public static void setEngine(Engine engine) {
        ShipFactory.engine = engine;
    }

    private static Engine engine;

    public static Entity createNewShip(Engine engine) {

        Entity ship = engine.createEntity();
        ship.add(new MovementComponent(0, 0));

        NetworkedComponent networkedComponent = new NetworkedComponent();
        networkedComponent.id = 100;
        networkedComponent.type = ObjectType.SHIP;
        ship.add(networkedComponent);

        TransformComponent shipTransformComponent = engine.createComponent(TransformComponent.class);
        shipTransformComponent.pos.set(5.0f, 1.0f, 0.0f);
        ship.add(shipTransformComponent);

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);
        movementComponent.velocity.set(0.25f,0);
        ship.add(movementComponent);
        BoundsComponent shipBoundsComponent = engine.createComponent(BoundsComponent.class);
        shipBoundsComponent.bounds.width = 1;
        shipBoundsComponent.bounds.height = 1;
        ship.add(shipBoundsComponent);
        ship.add(new WrapAroundComponent());
        ship.add(new ControlledComponent());

        engine.addEntity(ship);
        return ship;
    }

    public static void createPlayerJoinedShip(NewPlayerConnected newPlayerConnected) {

        Entity ship = engine.createEntity();
        ship.add(new MovementComponent(0, 0));

        NetworkedComponent networkedComponent = new NetworkedComponent();
        networkedComponent.id = newPlayerConnected.getId();
        networkedComponent.type = ObjectType.SHIP;
        ship.add(networkedComponent);

        TransformComponent shipTransformComponent = engine.createComponent(TransformComponent.class);
        shipTransformComponent.pos.set(5.0f, 1.0f, 0.0f);
        ship.add(shipTransformComponent);

        BoundsComponent shipBoundsComponent = engine.createComponent(BoundsComponent.class);
        shipBoundsComponent.bounds.width = 1;
        shipBoundsComponent.bounds.height = 1;
        ship.add(shipBoundsComponent);
        ship.add(new WrapAroundComponent());
        ship.add(new ControlledComponent());

        engine.addEntity(ship);
    }
}
