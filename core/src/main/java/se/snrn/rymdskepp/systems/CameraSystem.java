package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.CameraComponent;
import se.snrn.rymdskepp.components.TransformComponent;

public class CameraSystem extends IteratingSystem {
    private OrthographicCamera camera;

    public CameraSystem(OrthographicCamera camera) {
        super(Family.all(CameraComponent.class).get());
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CameraComponent cameraComponent = Mappers.cameraMapper.get(entity);
        TransformComponent transformComponent = Mappers.transformMapper.get(entity);
        if (cameraComponent != null) {
            camera.position.set(transformComponent.pos);
        }
    }
}
