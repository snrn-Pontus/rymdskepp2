package se.snrn.rymdskepp.server.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import se.snrn.rymdskepp.server.ashley.Mappers;
import se.snrn.rymdskepp.server.ashley.components.MovementComponent;
import se.snrn.rymdskepp.server.ashley.components.TransformComponent;


public class MovementSystem extends IteratingSystem {
    private Vector2 tmp = new Vector2();


    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos = Mappers.transformMapper.get(entity);
        MovementComponent mov = Mappers.movementMapper.get(entity);


        tmp.set(mov.accel).scl(deltaTime).rotateRad(pos.rotation);
        mov.velocity.add(tmp);

        tmp.set(mov.velocity).scl(deltaTime);
        pos.pos.add(tmp.x, tmp.y, 0.0f);

        pos.rotation += mov.rotation;

        System.out.println(pos);
    }
}