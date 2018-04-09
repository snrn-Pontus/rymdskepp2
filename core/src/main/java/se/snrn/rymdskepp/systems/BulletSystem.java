package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.BulletComponent;

public class BulletSystem extends IteratingSystem {
    public BulletSystem() {
        super(Family.all(BulletComponent.class).get());
    }

    @Override
    protected void processEntity(Entity bullet, float deltaTime) {
        BulletComponent bulletComponent = Mappers.bulletMapper.get(bullet);
        bulletComponent.addTime(deltaTime);

        if (bulletComponent.getDeltaTime() > BulletComponent.TIME_TO_LIVE) {
            getEngine().removeEntity(bullet);
        }


    }
}
