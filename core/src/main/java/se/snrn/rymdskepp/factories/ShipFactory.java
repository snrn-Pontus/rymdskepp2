package se.snrn.rymdskepp.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.NewPlayerConnected;
import se.snrn.rymdskepp.Resources;
import se.snrn.rymdskepp.State;
import se.snrn.rymdskepp.WebSocketClient;
import se.snrn.rymdskepp.components.*;
import se.snrn.rymdskepp.ships.JsonShipFactory;
import se.snrn.rymdskepp.ships.Ship;

public class ShipFactory {

    public Entity createShip(Engine engine, NewPlayerConnected newPlayerConnected, WebSocketClient webSocketClient, LightFactory lightFactory) {


        JsonShipFactory jsonShipFactory = new JsonShipFactory();
        Ship shipJsonObject = jsonShipFactory.getShips().get(newPlayerConnected.getShipType());


        TextureRegion shipTexture = Resources.getTextureRegion("ships/ship" + newPlayerConnected.getShipType());
        AnimationComponent animationComponent = AnimationComponent.create(engine);


        Animation shoot = AnimationFactory.createAnimation(shipJsonObject.getShootAnimation());
        Animation accelerate = AnimationFactory.createAnimation(shipJsonObject.getAccelerationAnimation());
        Animation normal = AnimationFactory.createAnimation(shipJsonObject.getNormalAnimation());
        accelerate.setPlayMode(Animation.PlayMode.LOOP);
        shoot.setPlayMode(Animation.PlayMode.LOOP);
        normal.setPlayMode(Animation.PlayMode.LOOP);

        animationComponent.addAnimation(State.SHOOT, shoot);
        animationComponent.addAnimation(State.ACCELERATING, accelerate);
        animationComponent.addAnimation(State.DEFAULT, normal);

        Entity ship = engine.createEntity();

        ship.add(animationComponent);
        ship.add(StateComponent.create(engine).set(State.ACCELERATING).setLooping(true));
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        textureComponent.setRegion(shipTexture);
        ship.add(textureComponent);
        ClientNetworkedComponent clientNetworkedComponent = engine.createComponent(ClientNetworkedComponent.class);
        clientNetworkedComponent.webSocketClient = webSocketClient;
        clientNetworkedComponent.id = newPlayerConnected.getId();

        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

        playerComponent.setId(newPlayerConnected.getId());
        playerComponent.setScore(0);
        ship.add(playerComponent);
        ship.add(clientNetworkedComponent);

        TransformComponent shipTransformComponent = engine.createComponent(TransformComponent.class);
        shipTransformComponent.pos.set(5.0f, 1.0f, 0);
        ship.add(shipTransformComponent);
        ship.add(engine.createComponent(ControlledComponent.class));

        NameTagComponent nameTagComponent = engine.createComponent(NameTagComponent.class);
        nameTagComponent.setName(newPlayerConnected.getName());
        ship.add(nameTagComponent);
        ConeLightComponent coneLightComponent = engine.createComponent(ConeLightComponent.class);
        coneLightComponent.setLight(lightFactory.createConeLight(shipJsonObject.getConeLight(), 5));
        coneLightComponent.setLightPosition(shipTransformComponent.pos.x, shipTransformComponent.pos.y);
        coneLightComponent.setPointLight(lightFactory.createPointLight(shipJsonObject.getPointLight(), 5));
        ship.add(coneLightComponent);


        ship.add(ParticleEmitterComponent.create(engine)
                .setParticleImage(Resources.getTextureRegion(shipJsonObject.getParticle()))
                .setParticleMinMaxScale(0.02f, 0.3f)
                .setSpawnType(ParticleSpawnType.DEFINED)
                .setSpawnRate(2f)
                .setZIndex(10f)
                .setParticleLifespans(0.2f, 0.5f)
                .setShouldFade(true)
                .setShouldLoop(true)
                .setAngleRange(170, 190)
                .setSpawnRange(1f, 1f)
                .setSpeed(5f, 10f)
                .setOffsetX(shipJsonObject.getOffSetX())
                .setOffsetY(shipJsonObject.getOffSetY()));

        engine.addEntity(ship);

        if (newPlayerConnected.getYou()) {
            ship.add(engine.createComponent(CameraComponent.class));
        }

        return ship;
    }
}
