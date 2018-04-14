package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.components.*;

public class ShipFactory {

    public Entity createShip(Engine engine, long id,String name, WebSocketClient webSocketClient) {
        TextureRegion shipTexture = new TextureRegion(new Texture("ship.png"));
        TextureRegion bulletTexture = new TextureRegion(new Texture("bullet.png"));
        Entity ship = engine.createEntity();
        ship.add(new TextureComponent(shipTexture));
        ship.add(new MovementComponent(0, 0));

        NetworkedComponent networkedComponent = new NetworkedComponent();
        networkedComponent.webSocketClient = webSocketClient;
        networkedComponent.id = id;
        ship.add(networkedComponent);

        TransformComponent shipTransformComponent = engine.createComponent(TransformComponent.class);
        shipTransformComponent.pos.set(5.0f, 1.0f, 0.0f);
        ship.add(shipTransformComponent);
        ship.add(new ControlledComponent());

        NameTagComponent nameTagComponent = engine.createComponent(NameTagComponent.class);
        nameTagComponent.setName(name);
        ship.add(nameTagComponent);

        engine.addEntity(ship);
        System.out.println("spawned: "+id);

        return ship;
    }

    public Entity createOtherShip(Engine engine, long id, WebSocketClient webSocketClient) {
        TextureRegion shipTexture = new TextureRegion(new Texture("ship2.png"));
        TextureRegion bulletTexture = new TextureRegion(new Texture("bullet.png"));
        Entity ship = engine.createEntity();
        ship.add(new TextureComponent(shipTexture));
        ship.add(new MovementComponent(0, 0));

        NetworkedComponent networkedComponent = new NetworkedComponent();
        networkedComponent.webSocketClient = webSocketClient;
        networkedComponent.id = id;
        ship.add(networkedComponent);

        TransformComponent shipTransformComponent = engine.createComponent(TransformComponent.class);
        shipTransformComponent.pos.set(5.0f, 1.0f, 0.0f);
        ship.add(shipTransformComponent);
        ship.add(new ControlledComponent());

        engine.addEntity(ship);
        return ship;
    }
}
