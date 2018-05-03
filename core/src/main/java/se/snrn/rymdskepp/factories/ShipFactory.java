package se.snrn.rymdskepp.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.NewPlayerConnected;
import se.snrn.rymdskepp.State;
import se.snrn.rymdskepp.WebSocketClient;
import se.snrn.rymdskepp.components.*;
import se.snrn.rymdskepp.ships.JsonShipFactory;
import se.snrn.rymdskepp.ships.Ship;

import java.util.ArrayList;

public class ShipFactory {

    public Entity createShip(Engine engine, NewPlayerConnected newPlayerConnected, WebSocketClient webSocketClient, LightFactory lightFactory) {



        JsonShipFactory jsonShipFactory = new JsonShipFactory();
        Ship shipJsonObject = jsonShipFactory.getShips().get(newPlayerConnected.getShipType());


        TextureRegion shipTexture = new TextureRegion(new Texture("ships/ship" + newPlayerConnected.getShipType() + ".png"));
        AnimationComponent animationComponent = AnimationComponent.create(engine);

//        Animation acceleration = new Animation<TextureRegion>(1f / 16f,
//                new TextureRegion(new Texture(Gdx.files.internal("ships/ship" + newPlayerConnected.getShipType() + "_acceleration" + ".png")))
//        );
        Animation normal = new Animation<TextureRegion>(1f / 16f,
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/mr_show_me00.png")))
        );


        Animation shoot = new Animation<TextureRegion>(1f / 16f,
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/mr_show_me00.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/mr_show_me01.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/mr_show_me02.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/mr_show_me03.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/mr_show_me04.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/mr_show_me05.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/mr_show_me06.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/mr_show_me07.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/mr_show_me08.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/mr_show_me09.png")))

        );
        Animation acceleration = new Animation<TextureRegion>(1f / 16f,
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/speed0.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/speed1.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/speed2.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/speed3.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/speed4.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/sebbe/speed5.png")))
        );
        acceleration.setPlayMode(Animation.PlayMode.LOOP);
        shoot.setPlayMode(Animation.PlayMode.LOOP);
        normal.setPlayMode(Animation.PlayMode.LOOP);
//        animationComponent.addAnimation(State.SHOOT, shoot);
//        animationComponent.addAnimation(State.ACCELERATING, acceleration);
//        animationComponent.addAnimation(State.DEFAULT, normal);
        TextureRegion bulletTexture = new TextureRegion(new Texture("ships/"+shipJsonObject.getBullets()));
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
                .setParticleImage(new TextureRegion(new Texture("bullet.png")))
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
