package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.components.*;

public class ShipFactory {

    public static Entity createShip(Engine engine, WebSocketClient webSocketClient){
        TextureRegion shipTexture = new TextureRegion(new Texture("ship.png"));
        TextureRegion bulletTexture = new TextureRegion(new Texture("bullet.png"));
        Entity ship = engine.createEntity();
        ship.add(new TextureComponent(shipTexture));
        ship.add(new MovementComponent(0, 0));

        NetworkedComponent networkedComponent = new NetworkedComponent();
        networkedComponent.webSocketClient = webSocketClient;
        networkedComponent.id = 100;
        ship.add(networkedComponent);

        TransformComponent shipTransformComponent = engine.createComponent(TransformComponent.class);
        shipTransformComponent.pos.set(5.0f, 1.0f, 0.0f);
        ship.add(shipTransformComponent);
        ship.add(new ControlledComponent());

        engine.addEntity(ship);
        return ship;
    }
    public static Entity createOtherShip(Engine engine, WebSocketClient webSocketClient){
        TextureRegion shipTexture = new TextureRegion(new Texture("ship2.png"));
        Entity ship = engine.createEntity();
        ship.add(new TextureComponent(shipTexture));
//        ship.add(new MovementComponent(0, 0));
        TransformComponent shipTransformComponent = engine.createComponent(TransformComponent.class);
        shipTransformComponent.pos.set(10.0f, 10.0f, 0.0f);
        ship.add(shipTransformComponent);

        NetworkedComponent networkedComponent = new NetworkedComponent();
        networkedComponent.webSocketClient = webSocketClient;
        networkedComponent.id = 150;
        ship.add(networkedComponent);
        engine.addEntity(ship);
        return ship;
    }
}
