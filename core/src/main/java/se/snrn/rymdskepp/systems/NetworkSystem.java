package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.NetworkObject;
import se.snrn.rymdskepp.ObjectType;
import se.snrn.rymdskepp.Rymdskepp;
import se.snrn.rymdskepp.components.NetworkedComponent;
import se.snrn.rymdskepp.components.TransformComponent;

public class NetworkSystem extends IteratingSystem {


    private Rymdskepp rymdskepp;

    public NetworkSystem(Rymdskepp rymdskepp) {
        super(Family.all(NetworkedComponent.class).get());
        this.rymdskepp = rymdskepp;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    public void handle(NetworkObject networkObject) {
        for (int i = 0; i < getEntities().size(); i++) {
            NetworkedComponent networkedComponent = Mappers.networkedMapper.get(getEntities().get(i));
            if (networkedComponent.id == networkObject.getId()) {
                if (networkObject.isRemove()) {

                    getEngine().removeEntity(getEntities().get(i));
                    break;
                }
                TransformComponent transformComponent = Mappers.transformMapper.get(getEntities().get(i));
                transformComponent.pos.set(networkObject.getCoordinates().getX(), networkObject.getCoordinates().getY(), 0);
                transformComponent.rotation = networkObject.getCoordinates().getRotation();
                break;
            } else if (networkObject.getObjectType() == ObjectType.BULLET &&
                    !rymdskepp.gameScreen.getSpawnedBullets().contains(networkObject.getId())) {

                rymdskepp.gameScreen.getSpawnedBullets().add(networkObject.getId());
                rymdskepp.getBulletsToSpawn().add(networkObject);
                break;
            }

        }

    }
}
