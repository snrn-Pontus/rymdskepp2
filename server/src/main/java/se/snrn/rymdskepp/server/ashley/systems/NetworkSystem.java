package se.snrn.rymdskepp.server.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import org.java_websocket.server.WebSocketServer;
import se.snrn.rymdskepp.Coordinates;

import se.snrn.rymdskepp.server.SimpleServer;
import se.snrn.rymdskepp.server.ashley.Mappers;
import se.snrn.rymdskepp.server.ashley.components.NetworkedComponent;
import se.snrn.rymdskepp.server.ashley.components.TransformComponent;

public class NetworkSystem extends IteratingSystem {


    private SimpleServer server;

    public NetworkSystem(SimpleServer server) {
        super(Family.all(NetworkedComponent.class).get());
        this.server = server;
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
            server.sendObject(coordinates);
//            networkedComponent.websocketManager.send(coordinates);
        }
    }
}
