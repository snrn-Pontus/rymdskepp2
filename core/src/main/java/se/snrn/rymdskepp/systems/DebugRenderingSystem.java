package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.TransformComponent;

public class DebugRenderingSystem extends SortedIteratingSystem {

    private Array<Entity> renderQueue;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;


    public DebugRenderingSystem(OrthographicCamera cam) {
        super(Family.all(
                TransformComponent.class).get(),
                (entityA, entityB) -> (int) Math.signum(Mappers.transformMapper.get(entityB).pos.z -
                        Mappers.transformMapper.get(entityA).pos.z));
        this.cam = cam;


        renderQueue = new Array<>();


        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        shapeRenderer.setProjectionMatrix(cam.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Entity entity : renderQueue) {
            TransformComponent transformComponent = Mappers.transformMapper.get(entity);


            Vector2 vector2 = new Vector2(transformComponent.pos.x, transformComponent.pos.y);
            vector2.scl(transformComponent.scale);


            shapeRenderer.circle(
                    vector2.x,
                    vector2.y,
                    0.125f,
                    12);
        }
        shapeRenderer.end();


        renderQueue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return cam;
    }
}
