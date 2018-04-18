package se.snrn.rymdskepp.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.WebSocketClient;
import se.snrn.rymdskepp.components.*;

public class ShipFactory {

    public Entity createShip(Engine engine, long id,String name, WebSocketClient webSocketClient,int shipType) {
        TextureRegion shipTexture = new TextureRegion(new Texture("ships/ship"+shipType+".png"));
        TextureRegion bulletTexture = new TextureRegion(new Texture("bullet.png"));
        Entity ship = engine.createEntity();
        ship.add(new TextureComponent(shipTexture));

        ClientNetworkedComponent clientNetworkedComponent = new ClientNetworkedComponent();
        clientNetworkedComponent.webSocketClient = webSocketClient;
        clientNetworkedComponent.id = id;

        ship.add(clientNetworkedComponent);

        TransformComponent shipTransformComponent = engine.createComponent(TransformComponent.class);
        shipTransformComponent.pos.set(5.0f, 1.0f, 0.0f);
        ship.add(shipTransformComponent);
        ship.add(new ControlledComponent());

        NameTagComponent nameTagComponent = engine.createComponent(NameTagComponent.class);
        nameTagComponent.setName(name);
        ship.add(nameTagComponent);

        engine.addEntity(ship);

        return ship;
    }
}
