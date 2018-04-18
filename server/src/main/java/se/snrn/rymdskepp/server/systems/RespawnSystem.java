package se.snrn.rymdskepp.server.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import se.snrn.rymdskepp.components.TransformComponent;
import se.snrn.rymdskepp.server.Console;
import se.snrn.rymdskepp.server.Mappers;
import se.snrn.rymdskepp.server.Player;
import se.snrn.rymdskepp.server.components.PlayerComponent;

public class RespawnSystem extends IntervalIteratingSystem {
    public RespawnSystem() {
        super(Family.all(PlayerComponent.class).get(), 1);
    }

    @Override
    protected void processEntity(Entity entity) {
        Player player = (Player) entity;
        PlayerComponent playerComponent = Mappers.playerMapper.get(player);



        if (!player.isSpawned()) {
            if(player.getShip() != null) {
                TransformComponent transformComponent = Mappers.transformMapper.get(player.getShip());
                transformComponent.pos.set(0,0,0);
            }
            if (playerComponent.getSpawnTimer() <= 0) {
                Console.getInstance().log("Should respawn");
                player.setSpawned(true);
            } else {
                playerComponent.setSpawnTimer(playerComponent.getSpawnTimer() - 1);
                player.setName(playerComponent.getSpawnTimer()+"");
                Console.getInstance().log(playerComponent.getName() + " respawning in " + playerComponent.getSpawnTimer());
            }
        }
    }
}
