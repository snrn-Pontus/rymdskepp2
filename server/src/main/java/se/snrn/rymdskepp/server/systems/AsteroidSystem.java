package se.snrn.rymdskepp.server.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import se.snrn.rymdskepp.server.factories.AsteroidFactory;
import se.snrn.rymdskepp.server.Mappers;
import se.snrn.rymdskepp.server.components.AsteroidComponent;

public class AsteroidSystem extends EntitySystem {

    public void hit(Entity asteroid, Entity bullet) {
        AsteroidComponent asteroidComponent = Mappers.asteroidMapper.get(asteroid);

        switch (asteroidComponent.size) {
            case SMALL:
                break;
            case MEDIUM:
                AsteroidFactory.small(getEngine(), asteroid, true);
                AsteroidFactory.small(getEngine(), asteroid, false);
                break;
            case BIG:
                AsteroidFactory.medium(getEngine(), asteroid, true);
                AsteroidFactory.medium(getEngine(), asteroid, false);
                break;
        }
    }


}
