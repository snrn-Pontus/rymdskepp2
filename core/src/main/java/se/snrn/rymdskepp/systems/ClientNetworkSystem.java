package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.*;
import se.snrn.rymdskepp.components.ClientNetworkedComponent;
import se.snrn.rymdskepp.components.StateComponent;
import se.snrn.rymdskepp.components.TransformComponent;

public class ClientNetworkSystem extends IteratingSystem {


    private Rymdskepp rymdskepp;

    public ClientNetworkSystem(Rymdskepp rymdskepp) {
        super(Family.all(ClientNetworkedComponent.class).get());
        this.rymdskepp = rymdskepp;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    public void handle(NetworkObject networkObject) {
        for (int i = 0; i < getEntities().size(); i++) {
            ClientNetworkedComponent clientNetworkedComponent = Mappers.networkedMapper.get(getEntities().get(i));

            if (clientNetworkedComponent.id == networkObject.getId()) {
                if (networkObject.isRemove()) {

                    getEngine().removeEntity(getEntities().get(i));
                    continue;
                }

                StateComponent stateComponent = Mappers.stateMapper.get(getEntities().get(i));
                if (stateComponent != null && stateComponent.get() != networkObject.getState()) {
                    System.out.println(networkObject.getState());
                    stateComponent.set(networkObject.getState());
                }

                TransformComponent transformComponent = SharedMappers.transformMapper.get(getEntities().get(i));
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
