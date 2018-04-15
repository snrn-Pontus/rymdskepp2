package se.snrn.rymdskepp.server.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import se.snrn.rymdskepp.ObjectType;
import se.snrn.rymdskepp.server.ashley.Mappers;
import se.snrn.rymdskepp.server.ashley.components.*;

import java.util.Random;


public class WeaponSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(WeaponComponent.class, TransformComponent.class).get());
    }

    public void shoot(Entity entity) {
        TransformComponent t = Mappers.transformMapper.get(entity);

        WeaponComponent weaponComponent = Mappers.weaponMapper.get(entity);
        Entity bullet = getEngine().createEntity();
        TransformComponent transformComponent = getEngine().createComponent(TransformComponent.class);
        transformComponent.pos.set(t.pos);
        transformComponent.rotation = t.rotation;
        bullet.add(transformComponent);
        CircleBoundsComponent circleBoundsComponent = getEngine().createComponent(CircleBoundsComponent.class);
        circleBoundsComponent.circle.radius = 0.125f;
        circleBoundsComponent.circle.setPosition(t.pos.x - 0.5f, t.pos.y - 0.5f);


        NetworkedComponent networkedComponent = getEngine().createComponent(NetworkedComponent.class);
        networkedComponent.type = ObjectType.BULLET;
        networkedComponent.setId(new Random().nextLong());
        bullet.add(networkedComponent);

        bullet.add(circleBoundsComponent);

        MovementComponent movementComponent = getEngine().createComponent(MovementComponent.class);

        movementComponent.velocity.set(0, 10).setAngleRad(t.rotation).rotate90(1);

        bullet.add(movementComponent);
        bullet.add(new BulletComponent());
        bullet.add(new WrapAroundComponent());

        getEngine().addEntity(bullet);
    }
}
