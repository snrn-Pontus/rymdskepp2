package se.snrn.rymdskepp.server.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.Coordinates;
import se.snrn.rymdskepp.server.WebSocketServer;
import se.snrn.rymdskepp.server.ashley.Mappers;
import se.snrn.rymdskepp.server.ashley.components.NetworkedComponent;
import se.snrn.rymdskepp.server.ashley.components.TransformComponent;

public class NetworkSystem extends IteratingSystem {


    private WebSocketServer webSocketServer;

    public NetworkSystem(WebSocketServer webSocketServer) {
        super(Family.all(NetworkedComponent.class, TransformComponent.class).get());
        this.webSocketServer = webSocketServer;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        NetworkedComponent networkedComponent = Mappers.networkedMapper.get(entity);
        TransformComponent transformComponent = Mappers.transformMapper.get(entity);

        Coordinates coordinates = new Coordinates();
        coordinates.setX(transformComponent.pos.x);
        coordinates.setY(transformComponent.pos.y);
        coordinates.setRotation(transformComponent.rotation);
        coordinates.setId(networkedComponent.id);
//        NetworkObject networkObject = new NetworkObject(networkedComponent.id, networkedComponent.type);
        webSocketServer.sendToAllPlayers(coordinates);
    }
}
