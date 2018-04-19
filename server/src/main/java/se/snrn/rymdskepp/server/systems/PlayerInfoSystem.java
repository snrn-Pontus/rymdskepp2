package se.snrn.rymdskepp.server.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.server.Console;
import se.snrn.rymdskepp.server.components.PlayerComponent;

public class PlayerInfoSystem extends IteratingSystem {
    public PlayerInfoSystem() {
        super(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
//        Console.getInstance().log(entity.getComponent(PlayerComponent.class).getName());
    }
}
