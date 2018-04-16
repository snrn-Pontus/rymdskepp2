package se.snrn.rymdskepp.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.NetworkObject;
import se.snrn.rymdskepp.components.ClientNetworkedComponent;
import se.snrn.rymdskepp.components.TextureComponent;
import se.snrn.rymdskepp.components.TransformComponent;

public class BulletFactory {

    public Entity createNewBullet(NetworkObject networkObject, Engine engine) {
        Entity bullet = engine.createEntity();
        TextureRegion bulletTexture = new TextureRegion(new Texture("bullet.png"));
        bullet.add(new TextureComponent(bulletTexture));

        ClientNetworkedComponent clientNetworkedComponent = new ClientNetworkedComponent();
        clientNetworkedComponent.id = networkObject.getId();
        bullet.add(clientNetworkedComponent);

        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.pos.set(-1,-1, 0.0f);
        bullet.add(transformComponent);

        return bullet;
    }
}
