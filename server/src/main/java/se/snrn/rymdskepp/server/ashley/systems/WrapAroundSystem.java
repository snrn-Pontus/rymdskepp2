package se.snrn.rymdskepp.server.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.server.ashley.Mappers;
import se.snrn.rymdskepp.server.ashley.components.TransformComponent;
import se.snrn.rymdskepp.server.ashley.components.WrapAroundComponent;

import static se.snrn.rymdskepp.server.ashley.systems.RenderingSystem.FRUSTUM_HEIGHT;
import static se.snrn.rymdskepp.server.ashley.systems.RenderingSystem.FRUSTUM_WIDTH;

public class WrapAroundSystem extends IteratingSystem {


    public WrapAroundSystem() {
        super(Family.all(WrapAroundComponent.class, TransformComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent t = Mappers.transformMapper.get(entity);

        t.pos.x %= FRUSTUM_WIDTH;
        t.pos.y %= FRUSTUM_HEIGHT;


        if (t.pos.x < 0) {
            t.pos.x = FRUSTUM_WIDTH;
            System.out.println("WRAPP!");
        }

        if (t.pos.y < 0) {
            t.pos.y = FRUSTUM_HEIGHT;
            System.out.println("WRAPP!");
        }
    }


}
