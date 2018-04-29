package se.snrn.rymdskepp.server.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import se.snrn.rymdskepp.Coordinates;
import se.snrn.rymdskepp.ExplosionMessage;
import se.snrn.rymdskepp.NetworkObject;
import se.snrn.rymdskepp.ObjectType;
import se.snrn.rymdskepp.components.ControlledComponent;
import se.snrn.rymdskepp.components.TransformComponent;
import se.snrn.rymdskepp.server.*;
import se.snrn.rymdskepp.server.components.*;

import java.util.HashMap;

public class CollisionSystem extends EntitySystem {
    private Engine engine;
    private ImmutableArray<Entity> bullets;
    private ImmutableArray<Entity> ships;
    private WebSocketServer webSocketServer;
    private HashMap<Long, Player> playerMap;


    public CollisionSystem(WebSocketServer webSocketServer) {

        this.webSocketServer = webSocketServer;
        this.playerMap = GameState.getInstance().getIdToPlayerMap();
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = engine;

        bullets = engine.getEntitiesFor(Family.all(se.snrn.rymdskepp.server.components.BulletComponent.class, TransformComponent.class, se.snrn.rymdskepp.server.components.CircleBoundsComponent.class).get());
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
                NetworkedComponent bulletNetworkComponent = Mappers.networkedMapper.get(bullet);

                if (shipBounds.bounds.contains(bulletBounds.circle)) {
                    if (bulletComponent.getId() != shipNetworkComponent.getId()) {

                        NetworkObject networkObject = new NetworkObject();
                        networkObject.setId(bulletNetworkComponent.getId());
                        networkObject.setObjectType(ObjectType.BULLET);
                        networkObject.setRemove(true);
                        networkObject.setCoordinates(new Coordinates());
                        webSocketServer.sendToAllPlayers(networkObject);


                        ship.remove(WrapAroundComponent.class);
                        Player player = GameState.getInstance().getShipPlayerMap().get(ship);
                        PlayerComponent playerComponent = Mappers.playerMapper.get(player);
                        TransformComponent transformComponent = Mappers.transformMapper.get(player.getShip());

                        player.setDestroyed(true);
                        webSocketServer.sendToAllPlayers(new ExplosionMessage(new Coordinates(transformComponent.pos.x, transformComponent.pos.y, player.getId(), 0f)));
                        playerComponent.setSpawnTimer(3);
                        Console.getInstance().log(bulletComponent.getId() + " killed " + playerComponent.getName());
                        Player killer = playerMap.get(bulletComponent.getId());
                        killer.setScore(killer.getScore()+1);
                        getEngine().removeEntity(bullet);


                        if (player.getShip() != null && player.getDestroyed()) {
                            MovementComponent movementComponent = Mappers.movementMapper.get(player.getShip());
                            transformComponent.pos.set(-10, -10, 0);
                            movementComponent.acceleration.setZero();
                            movementComponent.velocity.setZero();
                        }
                    }
                }
            }

        }
    }
}