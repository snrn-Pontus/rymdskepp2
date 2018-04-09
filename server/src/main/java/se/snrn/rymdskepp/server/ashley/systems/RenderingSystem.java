package se.snrn.rymdskepp.server.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import se.snrn.rymdskepp.server.ashley.Mappers;
import se.snrn.rymdskepp.server.ashley.components.BoundsComponent;
import se.snrn.rymdskepp.server.ashley.components.CircleBoundsComponent;
import se.snrn.rymdskepp.server.ashley.components.TextureComponent;
import se.snrn.rymdskepp.server.ashley.components.TransformComponent;

import java.util.Comparator;

public class RenderingSystem extends SortedIteratingSystem {
    public static final float FRUSTUM_WIDTH = 16;
    public static final float FRUSTUM_HEIGHT = 9;
    static final float PIXELS_TO_METRES = 1.0f / 32.0f;

    private Batch batch;
    private Array<Entity> renderQueue;
    private OrthographicCamera cam;


    private ShapeRenderer shapeRenderer;

    public RenderingSystem(Batch batch) {
        super(Family.all(
                TransformComponent.class, TextureComponent.class)
                        .one(CircleBoundsComponent.class, BoundsComponent.class)
                        .get(),
                new Comparator<Entity>() {
                    @Override
                    public int compare(Entity entityA, Entity entityB) {
                        return (int) Math.signum(Mappers.transformMapper.get(entityB).pos.z -
                                Mappers.transformMapper.get(entityA).pos.z);
                    }
                });
        shapeRenderer = new ShapeRenderer();


        renderQueue = new Array<Entity>();


        this.batch = batch;

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = Mappers.textureMapper.get(entity);

            if (tex.region == null) {
                continue;
            }

            TransformComponent t = Mappers.transformMapper.get(entity);

            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();
            float originX = width * 0.5f;
            float originY = height * 0.5f;


            batch.draw(tex.region,
                    t.pos.x - originX, t.pos.y - originY,
                    originX, originY,
                    width, height,
                    t.scale.x * PIXELS_TO_METRES, t.scale.y * PIXELS_TO_METRES,
                    MathUtils.radiansToDegrees * t.rotation);
        }

        batch.end();


        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Entity entity : renderQueue) {
            if (Mappers.circleBoundsComponentMapper.has(entity)) {
                Circle bounds = Mappers.circleBoundsComponentMapper.get(entity).circle;
                if (bounds != null) {
                    shapeRenderer.circle(bounds.x, bounds.y, bounds.radius, 24);
                }
            }
        }
        shapeRenderer.flush();
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
