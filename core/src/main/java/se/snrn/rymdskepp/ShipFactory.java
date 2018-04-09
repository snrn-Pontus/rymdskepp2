package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.components.*;

public class ShipFactory {

    public static Entity createShip(Engine engine, WebsocketManager websocketManager){
        TextureRegion shipTexture = new TextureRegion(new Texture("ship.png"));
        TextureRegion bulletTexture = new TextureRegion(new Texture("bullet.png"));
        Entity ship = engine.createEntity();
        ship.add(new TextureComponent(shipTexture));
        ship.add(new MovementComponent(0, 0));
        ship.add(new WeaponComponent(bulletTexture));

        NetworkedComponent networkedComponent = new NetworkedComponent();
        networkedComponent.websocketManager = websocketManager;
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
    public static Entity createOtherShip(Engine engine,WebsocketManager websocketManager){
        TextureRegion shipTexture = new TextureRegion(new Texture("ship2.png"));
        Entity ship = engine.createEntity();
        ship.add(new TextureComponent(shipTexture));
//        ship.add(new MovementComponent(0, 0));
        TransformComponent shipTransformComponent = engine.createComponent(TransformComponent.class);
        shipTransformComponent.pos.set(10.0f, 10.0f, 0.0f);
        ship.add(shipTransformComponent);

        NetworkedComponent networkedComponent = new NetworkedComponent();
        networkedComponent.websocketManager = websocketManager;
        networkedComponent.id = 150;
        ship.add(networkedComponent);


        BoundsComponent shipBoundsComponent = engine.createComponent(BoundsComponent.class);
        shipBoundsComponent.bounds.width = 1;
        shipBoundsComponent.bounds.height = 1;
        ship.add(shipBoundsComponent);
        ship.add(new WrapAroundComponent());

        engine.addEntity(ship);
        websocketManager.setOtherShip(ship);
        return ship;
    }
}
