package se.snrn.rymdskepp.server.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import se.snrn.rymdskepp.components.TransformComponent;
import se.snrn.rymdskepp.server.Mappers;
import se.snrn.rymdskepp.server.components.NetworkedComponent;
import se.snrn.rymdskepp.server.components.WeaponComponent;
import se.snrn.rymdskepp.server.factories.BulletFactory;


public class WeaponSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(WeaponComponent.class, TransformComponent.class).get());
    }

    public void shoot(Entity entity) {
        TransformComponent transformComponent = Mappers.transformMapper.get(entity);
        NetworkedComponent networkedComponent = Mappers.networkedMapper.get(entity);

        BulletFactory.createNewBullet(networkedComponent.getId(), transformComponent, getEngine(), entity);


    }
}
