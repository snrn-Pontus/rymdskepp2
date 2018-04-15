package se.snrn.rymdskepp;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import se.snrn.rymdskepp.components.MovementComponent;
import se.snrn.rymdskepp.components.NetworkedComponent;
import se.snrn.rymdskepp.components.TextureComponent;
import se.snrn.rymdskepp.components.TransformComponent;

public class BulletFactory {

    public Entity createNewBullet(NetworkObject networkObject, Engine engine) {
        Entity bullet = engine.createEntity();
        TextureRegion bulletTexture = new TextureRegion(new Texture("bullet.png"));
        bullet.add(new TextureComponent(bulletTexture));
        bullet.add(new MovementComponent(0, 0));

        NetworkedComponent networkedComponent = new NetworkedComponent();
        networkedComponent.id = networkObject.getId();
        bullet.add(networkedComponent);

        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.pos.set(-1,-1, 0.0f);
        bullet.add(transformComponent);

        return bullet;
    }
}
