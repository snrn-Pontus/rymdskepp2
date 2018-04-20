package se.snrn.rymdskepp.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.WebSocketClient;
import se.snrn.rymdskepp.components.*;

public class ShipFactory {

    public Entity createShip(Engine engine, long id,String name, WebSocketClient webSocketClient,int shipType,LightFactory lightFactory) {
        TextureRegion shipTexture = new TextureRegion(new Texture("ships/ship"+shipType+".png"));
        TextureRegion bulletTexture = new TextureRegion(new Texture("bullet.png"));
        Entity ship = engine.createEntity();
        ship.add(new TextureComponent(shipTexture));

        ClientNetworkedComponent clientNetworkedComponent = new ClientNetworkedComponent();
        clientNetworkedComponent.webSocketClient = webSocketClient;
        clientNetworkedComponent.id = id;

        ship.add(clientNetworkedComponent);

        TransformComponent shipTransformComponent = engine.createComponent(TransformComponent.class);
        shipTransformComponent.pos.set(5.0f, 1.0f, 0);
        ship.add(shipTransformComponent);
        ship.add(new ControlledComponent());

        NameTagComponent nameTagComponent = engine.createComponent(NameTagComponent.class);
        nameTagComponent.setName(name);
        ship.add(nameTagComponent);
        ConeLightComponent coneLightComponent = engine.createComponent(ConeLightComponent.class);
        coneLightComponent.setLight(lightFactory.createConeLight(Color.YELLOW,5));
        coneLightComponent.setLightPosition(shipTransformComponent.pos.x,shipTransformComponent.pos.y);
        coneLightComponent.setPointLight(lightFactory.createPointLight(Color.WHITE,5));
        ship.add(coneLightComponent);


        ship.add(ParticleEmitterComponent.create(engine)
                .setParticleImage(new TextureRegion(new Texture("bullet.png")))
                .setParticleMinMaxScale(0.02f, 0.3f)
                .setSpawnType(ParticleSpawnType.FROM_CENTER)
                .setSpawnRate(2f)
                .setZIndex(10f)
                .setParticleLifespans(0.2f, 0.5f)
                .setShouldFade(true)
                .setShouldLoop(true)
                .setAngleRange(170,190)
                .setSpawnRange(1f, 1f)
                .setSpeed(5f, 10f));

        engine.addEntity(ship);

        return ship;
    }
}
