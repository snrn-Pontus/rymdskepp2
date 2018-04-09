package se.snrn.rymdskepp.server.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.server.ashley.components.*;

public class ShipFactory {


    public static Entity createNewShip(Engine engine) {
//        TextureRegion shipTexture = new TextureRegion(new Texture("ship.png"));
//        TextureRegion bulletTexture = new TextureRegion(new Texture("bullet.png"));
        Entity ship = engine.createEntity();
//        ship.add(new TextureComponent(shipTexture));
        ship.add(new MovementComponent(0, 0));
//        ship.add(new WeaponComponent(bulletTexture));

        NetworkedComponent networkedComponent = new NetworkedComponent();
        networkedComponent.id = 100;
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
        return ship;
    }
}
