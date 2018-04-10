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
        NetworkedComponent networkedComponent = Mappers.networkedMapper.get(entity);
        TransformComponent transformComponent = Mappers.transformMapper.get(entity);

        if (networkedComponent.id == 100) {
            Coordinates coordinates = new Coordinates();
            coordinates.setX(transformComponent.pos.x);
            coordinates.setY(transformComponent.pos.y);
            coordinates.setRotation(transformComponent.rotation);
            coordinates.setId(networkedComponent.id);
        }
    }

    public void handle(Coordinates networkObject) {
        TransformComponent transformComponent = Mappers.transformMapper.get(getEntities().get(0));
        transformComponent.pos.set(networkObject.getX(),networkObject.getY(),0);
        transformComponent.rotation = networkObject.getRotation();
    }
}
