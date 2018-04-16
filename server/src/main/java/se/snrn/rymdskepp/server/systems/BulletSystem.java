package se.snrn.rymdskepp.server.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.Coordinates;
import se.snrn.rymdskepp.NetworkObject;
import se.snrn.rymdskepp.ObjectType;
import se.snrn.rymdskepp.server.Mappers;
import se.snrn.rymdskepp.server.WebSocketServer;
import se.snrn.rymdskepp.server.components.BulletComponent;
import se.snrn.rymdskepp.server.components.NetworkedComponent;


public class BulletSystem extends IteratingSystem {
    private WebSocketServer webSocketServer;

    public BulletSystem(WebSocketServer webSocketServer) {
        super(Family.all(BulletComponent.class).get());
        this.webSocketServer = webSocketServer;
    }

    @Override
    protected void processEntity(Entity bullet, float deltaTime) {
        BulletComponent bulletComponent = Mappers.bulletMapper.get(bullet);
        bulletComponent.addTime(deltaTime);

        if (bulletComponent.getDeltaTime() > BulletComponent.TIME_TO_LIVE) {
            NetworkObject networkObject = new NetworkObject();
            NetworkedComponent networkedComponent = Mappers.networkedMapper.get(bullet);
            networkObject.setId(networkedComponent.getId());
            networkObject.setObjectType(ObjectType.BULLET);
            networkObject.setRemove(true);
            networkObject.setCoordinates(new Coordinates());
            webSocketServer.sendToAllPlayers(networkObject);
            getEngine().removeEntity(bullet);
        }


    }
}
