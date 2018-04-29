package se.snrn.rymdskepp.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.WebSocketClient;
import se.snrn.rymdskepp.components.*;

import java.util.ArrayList;

public class ShipFactory {

    public Entity createShip(Engine engine, long id, String name, WebSocketClient webSocketClient, int shipType, LightFactory lightFactory) {
        TextureRegion shipTexture = new TextureRegion(new Texture("ships/ship" + shipType + ".png"));
        AnimationComponent animationComponent = AnimationComponent.create(engine);


        ArrayList<TextureRegion> textureRegions = new ArrayList<>();

        Animation animation = new Animation<TextureRegion>(1f / 16f, new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship00.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship01.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship02.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship03.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship04.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship05.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship06.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship07.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship08.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship09.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship10.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship11.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship12.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship13.png"))),
                new TextureRegion(new Texture(Gdx.files.internal("ships/belal/belals_spaceship14.png"))));
        animationComponent.addAnimation("shoot", animation);
        TextureRegion bulletTexture = new TextureRegion(new Texture("bullet.png"));
        Entity ship = engine.createEntity();

        ship.add(animationComponent);
        ship.add(StateComponent.create(engine).set("shoot").setLooping(true));
        ship.add(new TextureComponent());

        ClientNetworkedComponent clientNetworkedComponent = new ClientNetworkedComponent();
        clientNetworkedComponent.webSocketClient = webSocketClient;
        clientNetworkedComponent.id = id;

        PlayerComponent playerComponent = engine.createComponent(PlayerComponent.class);

        playerComponent.setId(id);
        playerComponent.setScore(0);
        ship.add(playerComponent);
        ship.add(clientNetworkedComponent);

        TransformComponent shipTransformComponent = engine.createComponent(TransformComponent.class);
        shipTransformComponent.pos.set(5.0f, 1.0f, 0);
        ship.add(shipTransformComponent);
        ship.add(new ControlledComponent());

        NameTagComponent nameTagComponent = engine.createComponent(NameTagComponent.class);
        nameTagComponent.setName(name);
        ship.add(nameTagComponent);
        ConeLightComponent coneLightComponent = engine.createComponent(ConeLightComponent.class);
        coneLightComponent.setLight(lightFactory.createConeLight(Color.YELLOW, 5));
        coneLightComponent.setLightPosition(shipTransformComponent.pos.x, shipTransformComponent.pos.y);
        coneLightComponent.setPointLight(lightFactory.createPointLight(Color.WHITE, 5));
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
                .setAngleRange(170, 190)
                .setSpawnRange(1f, 1f)
                .setSpeed(5f, 10f));

        engine.addEntity(ship);

        return ship;
    }
}
