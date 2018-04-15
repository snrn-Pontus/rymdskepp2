package se.snrn.rymdskepp.server.ashley.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import se.snrn.rymdskepp.Coordinates;
import se.snrn.rymdskepp.NetworkObject;
import se.snrn.rymdskepp.ObjectType;
import se.snrn.rymdskepp.server.GameState;
import se.snrn.rymdskepp.server.WebSocketServer;
import se.snrn.rymdskepp.server.ashley.Mappers;
import se.snrn.rymdskepp.server.ashley.components.*;

public class CollisionSystem extends EntitySystem {
    private Engine engine;
    private ImmutableArray<Entity> bullets;
    private ImmutableArray<Entity> ships;
    private WebSocketServer webSocketServer;


    public CollisionSystem(WebSocketServer webSocketServer) {

        this.webSocketServer = webSocketServer;
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;

        bullets = engine.getEntitiesFor(Family.all(BulletComponent.class, TransformComponent.class, CircleBoundsComponent.class).get());
        ships = engine.getEntitiesFor(Family.all(ControlledComponent.class).get());

    }


    @Override
    public void update(float deltaTime) {
        for (int i = 0; i < bullets.size(); ++i) {
            Entity bullet = bullets.get(i);
            CircleBoundsComponent bulletBounds = Mappers.circleBoundsComponentMapper.get(bullet);
            BulletComponent bulletComponent = Mappers.bulletMapper.get(bullet);

            for (int j = 0; j < ships.size(); ++j) {
                Entity ship = ships.get(j);

                BoundsComponent shipBounds = Mappers.boundsMapper.get(ship);
                NetworkedComponent shipNetworkComponent = Mappers.networkedMapper.get(ship);

                if (shipBounds.bounds.contains(bulletBounds.circle)) {
                    if (bulletComponent.getId() != shipNetworkComponent.getId()) {
                        System.out.println("Hit!");
                        NetworkObject networkObject = new NetworkObject();
                        networkObject.setId(shipNetworkComponent.getId());
                        networkObject.setObjectType(ObjectType.SHIP);
                        networkObject.setRemove(true);
                        networkObject.setCoordinates(new Coordinates());
                        webSocketServer.sendToAllPlayers(networkObject);

                        getEngine().removeEntity(ship);
                    }
                }
            }

        }


    }
}