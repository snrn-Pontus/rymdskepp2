package se.snrn.rymdskepp.server.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import se.snrn.rymdskepp.ObjectType;
import se.snrn.rymdskepp.components.ControlledComponent;
import se.snrn.rymdskepp.server.components.*;
import se.snrn.rymdskepp.components.TransformComponent;
import se.snrn.rymdskepp.server.ships.Ship;

import java.util.ArrayList;

public class ShipFactory {

    private Engine engine;
    private ArrayList<Ship> ships;

    public ShipFactory(Engine engine, ArrayList<Ship> ships) {
        this.engine = engine;
        this.ships = ships;
    }

    public Entity createNewShip(Engine engine, long id, String name, int shipType) {

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

        WeaponComponent weaponComponent = engine.createComponent(WeaponComponent.class);
        weaponComponent.setCanons(ships.get(shipType).getCanons());
        weaponComponent.setShipType(shipType);
        ship.add(weaponComponent);

        ship.add(StateComponent.create(engine));

        engine.addEntity(ship);
        return ship;
    }
}
