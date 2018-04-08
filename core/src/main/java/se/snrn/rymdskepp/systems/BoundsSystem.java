package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.components.BoundsComponent;
import se.snrn.rymdskepp.components.TransformComponent;

public class BoundsSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> tm;
    private ComponentMapper<BoundsComponent> bm;

    public BoundsSystem() {
        super(Family.all(BoundsComponent.class, TransformComponent.class).get());

        tm = ComponentMapper.getFor(TransformComponent.class);
        bm = ComponentMapper.getFor(BoundsComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos = tm.get(entity);
        BoundsComponent bounds = bm.get(entity);

        bounds.bounds.x = pos.pos.x - bounds.bounds.width * 0.5f;
        bounds.bounds.y = pos.pos.y - bounds.bounds.height * 0.5f;


    }


}