package se.snrn.rymdskepp.server.ashley;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import se.snrn.rymdskepp.ObjectType;
import se.snrn.rymdskepp.server.ashley.components.*;


public class BulletFactory {

    public static int id = 0;

    public static Entity createNewBullet(long id, TransformComponent t, Engine engine) {
        Entity bullet = engine.createEntity();

        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.pos.set(t.pos);
        transformComponent.rotation = t.rotation;
        bullet.add(transformComponent);
        CircleBoundsComponent circleBoundsComponent = engine.createComponent(CircleBoundsComponent.class);
        circleBoundsComponent.circle.radius = 0.125f;
        circleBoundsComponent.circle.setPosition(t.pos.x - 0.5f, t.pos.y - 0.5f);


        NetworkedComponent networkedComponent = engine.createComponent(NetworkedComponent.class);
        networkedComponent.type = ObjectType.BULLET;
        networkedComponent.setId(BulletFactory.id++);
        bullet.add(networkedComponent);

        bullet.add(circleBoundsComponent);

        MovementComponent movementComponent = engine.createComponent(MovementComponent.class);

        movementComponent.velocity.set(0, 1).setAngleRad(t.rotation).rotate90(1);

        bullet.add(movementComponent);
        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.setId(id);
        bullet.add(bulletComponent);
        bullet.add(new WrapAroundComponent());


        engine.addEntity(bullet);

        return bullet;
    }
}
