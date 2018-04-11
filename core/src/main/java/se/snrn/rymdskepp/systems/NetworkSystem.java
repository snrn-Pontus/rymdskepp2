package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.Coordinates;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.NetworkObject;
import se.snrn.rymdskepp.components.NetworkedComponent;
import se.snrn.rymdskepp.components.TransformComponent;

public class NetworkSystem extends IteratingSystem {


    public NetworkSystem() {
        super(Family.all(NetworkedComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    public void handle(Coordinates coordinates) {
        for (int i = 0; i < getEntities().size(); i++) {
            NetworkedComponent networkedComponent = Mappers.networkedMapper.get(getEntities().get(i));
            if(networkedComponent.id == coordinates.getId()) {
                TransformComponent transformComponent = Mappers.transformMapper.get(getEntities().get(i));
                transformComponent.pos.set(coordinates.getX(), coordinates.getY(), 0);
                transformComponent.rotation = coordinates.getRotation();
            }

        }

    }
}
