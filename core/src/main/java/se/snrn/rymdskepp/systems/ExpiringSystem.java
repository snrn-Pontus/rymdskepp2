package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.ExpiringComponent;

public class ExpiringSystem extends IntervalIteratingSystem {

    public ExpiringSystem() {
        super(Family.all(ExpiringComponent.class).get(), 0.2f);
    }

    @Override
    protected void processEntity(Entity entity) {
        ExpiringComponent expiringComponent = Mappers.expiringMapper.get(entity);
        expiringComponent.setTimeAlive(expiringComponent.getTimeAlive()+0.2f);
        if(expiringComponent.shouldDie()){
            getEngine().removeEntity(entity);
        }
    }
}
