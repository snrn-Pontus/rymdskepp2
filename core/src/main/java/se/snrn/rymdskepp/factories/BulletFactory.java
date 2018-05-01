package se.snrn.rymdskepp.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.GameScreen;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.NetworkObject;
import se.snrn.rymdskepp.SoundEnum;
import se.snrn.rymdskepp.components.*;

public class BulletFactory {

    public Entity createNewBullet(NetworkObject networkObject, Engine engine, LightFactory lightFactory) {
        Entity bullet = engine.createEntity();

//        PlayerComponent playerComponent = Mappers.playerMapper.get(playerWhoShot);

        TextureRegion bulletTexture = new TextureRegion(new Texture("ships/bullet0.png"));
        bullet.add(new TextureComponent(bulletTexture));

        ClientNetworkedComponent clientNetworkedComponent = new ClientNetworkedComponent();
        clientNetworkedComponent.id = networkObject.getId();
        bullet.add(clientNetworkedComponent);

        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.pos.set(-10, -10, 0.0f);
        bullet.add(transformComponent);
        LightComponent lightComponent = engine.createComponent(LightComponent.class);
        lightComponent.setLight(lightFactory.createLight(Color.WHITE, 5f));
        lightComponent.setLightPosition(transformComponent.pos.x, transformComponent.pos.y);
        bullet.add(lightComponent);



        GameScreen.soundSignal.dispatch(SoundEnum.SHOOT);

        return bullet;
    }
}
