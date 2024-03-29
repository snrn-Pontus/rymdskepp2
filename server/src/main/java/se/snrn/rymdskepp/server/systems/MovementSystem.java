package se.snrn.rymdskepp.server.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import se.snrn.rymdskepp.SharedMappers;
import se.snrn.rymdskepp.components.TransformComponent;
import se.snrn.rymdskepp.server.GameState;
import se.snrn.rymdskepp.server.Mappers;
import se.snrn.rymdskepp.server.components.MovementComponent;


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

        mov.velocity.set(mov.velocity.clamp(0,GameState.getInstance().getMaxAcceleration()));

        tmp.set(mov.velocity).scl(deltaTime);
        tmp.clamp(0, GameState.getInstance().getMaxSpeed());
        pos.pos.add(tmp.x, tmp.y, 0.0f);

        pos.rotation += (mov.rotation) * deltaTime;



    }
}