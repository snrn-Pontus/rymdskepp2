package se.snrn.rymdskepp.server.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.Coordinates;
import se.snrn.rymdskepp.NetworkObject;
import se.snrn.rymdskepp.components.TransformComponent;
import se.snrn.rymdskepp.server.Mappers;
import se.snrn.rymdskepp.server.WebSocketServer;
import se.snrn.rymdskepp.server.components.BulletComponent;
import se.snrn.rymdskepp.server.components.NetworkedComponent;
import se.snrn.rymdskepp.server.components.StateComponent;

public class NetworkSystem extends IteratingSystem {

    Coordinates coordinates;
    private NetworkObject networkObject;
    private WebSocketServer webSocketServer;
    private int counter = 0;

    public NetworkSystem(WebSocketServer webSocketServer) {
        super(Family.all(NetworkedComponent.class, TransformComponent.class).get());
        this.webSocketServer = webSocketServer;
        networkObject = new NetworkObject();
        coordinates = new Coordinates();
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        NetworkedComponent networkedComponent = Mappers.networkedMapper.get(entity);
        TransformComponent transformComponent = Mappers.transformMapper.get(entity);
        StateComponent stateComponent = Mappers.stateMapper.get(entity);
        BulletComponent bulletComponent = Mappers.bulletMapper.get(entity);


        coordinates.setX(transformComponent.pos.x);
        coordinates.setY(transformComponent.pos.y);
        coordinates.setRotation(transformComponent.rotation);
        coordinates.setId(networkedComponent.id);


        networkObject.setCoordinates(coordinates);
        networkObject.setId(networkedComponent.id);
        networkObject.setObjectType(networkedComponent.type);
        networkObject.setRemove(false);
        counter++;

        if (bulletComponent != null) {
            networkObject.setShipType(bulletComponent.getShipType());
        }

        if (stateComponent != null && stateComponent.get() != null) {
            networkObject.setState(stateComponent.get());
        }


        webSocketServer.sendToAllPlayers(networkObject);
    }
}
