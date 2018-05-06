package se.snrn.rymdskepp.server.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import se.snrn.rymdskepp.ObjectType;
import se.snrn.rymdskepp.components.TransformComponent;
import se.snrn.rymdskepp.server.Mappers;
import se.snrn.rymdskepp.server.components.*;

import static com.badlogic.gdx.math.MathUtils.radDeg;


public class BulletFactory {

    public static int id = 0;

    public static Entity createNewBullet(long id, TransformComponent t, Engine engine, Entity owner, WeaponComponent weaponComponent) {
        Entity bullet = engine.createEntity();

        Vector2 canon = weaponComponent.getCurrentCanon();
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);



        Vector2 tmp = new Vector2();

        float x = t.pos.x;
        float y = t.pos.y;
        tmp.set(canon.x, canon.y);
        tmp.rotate(radDeg * t.rotation);
        x += tmp.x;
        y += tmp.y;


        transformComponent.pos.set(x, y, 10);
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
        MovementComponent ownerMovementComponent = Mappers.movementMapper.get(owner);

        movementComponent.velocity.set(0, 10).setAngleRad(t.rotation).rotate90(1);

        PlayerComponent playerComponent = Mappers.playerMapper.get(owner);

        int shipType = weaponComponent.getShipType();

        bullet.add(movementComponent);
        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.setId(id);
        bulletComponent.setShipType(shipType);
        bullet.add(bulletComponent);
        bullet.add(new WrapAroundComponent());


        engine.addEntity(bullet);

        return bullet;
    }
}
