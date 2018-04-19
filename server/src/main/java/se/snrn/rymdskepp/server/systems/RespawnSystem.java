package se.snrn.rymdskepp.server.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import se.snrn.rymdskepp.components.TransformComponent;
import se.snrn.rymdskepp.server.Console;
import se.snrn.rymdskepp.server.Mappers;
import se.snrn.rymdskepp.server.Player;
import se.snrn.rymdskepp.server.components.MovementComponent;
import se.snrn.rymdskepp.server.components.PlayerComponent;
import se.snrn.rymdskepp.server.components.WrapAroundComponent;

public class RespawnSystem extends IntervalIteratingSystem {
    public RespawnSystem() {
        super(Family.all(PlayerComponent.class).get(), 1);
    }

    @Override
    protected void processEntity(Entity entity) {
        Player player = (Player) entity;
        PlayerComponent playerComponent = Mappers.playerMapper.get(player);

        if (player.getDestroyed()) {

            if (playerComponent.getSpawnTimer() <= 0) {
                player.setDestroyed(false);
                player.getShip().add(new WrapAroundComponent());
                TransformComponent transformComponent = Mappers.transformMapper.get(player.getShip());
                transformComponent.pos.set(5,5,0);
            } else {
                playerComponent.setSpawnTimer(playerComponent.getSpawnTimer() - 1);
                Console.getInstance().log(playerComponent.getName() + " respawning in " + playerComponent.getSpawnTimer());
            }
        }
    }
}
