package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.CameraComponent;
import se.snrn.rymdskepp.components.TransformComponent;

public class CameraSystem extends IteratingSystem {
    private OrthographicCamera camera;
    private Vector2 lastPos = new Vector2();
    private Vector2 thisPos = new Vector2();
    public CameraSystem(OrthographicCamera camera) {
        super(Family.all(CameraComponent.class).get());
        this.camera = camera;
        camera.zoom = 0.5f;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CameraComponent cameraComponent = Mappers.cameraMapper.get(entity);
        TransformComponent transformComponent = Mappers.transformMapper.get(entity);
        if (cameraComponent != null) {
            camera.position.set(transformComponent.pos);
        }
//
//        thisPos.set(transformComponent.pos.x,transformComponent.pos.y);
//
//        float sub = thisPos.dst(lastPos);
//        camera.zoom = sub*2;
//
//        lastPos.set(transformComponent.pos.x,transformComponent.pos.y);

    }
}
