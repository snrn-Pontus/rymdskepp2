package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.SharedMappers;
import se.snrn.rymdskepp.components.MovementComponent;
import se.snrn.rymdskepp.components.TransformComponent;



public class MovementSystem extends IteratingSystem {
    private Vector2 tmp = new Vector2();


    public MovementSystem() {
        super(Family.all(TransformComponent.class, MovementComponent.class).get());
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        TransformComponent pos = SharedMappers.transformMapper.get(entity);
        MovementComponent mov = Mappers.movementMapper.get(entity);


        tmp.set(mov.acceleration).scl(deltaTime).rotateRad(pos.rotation);
        mov.velocity.add(tmp);

        tmp.set(mov.velocity).scl(deltaTime);
        pos.pos.add(tmp.x, tmp.y, 0.0f);

        pos.rotation += (mov.rotation) * deltaTime;


    }
}