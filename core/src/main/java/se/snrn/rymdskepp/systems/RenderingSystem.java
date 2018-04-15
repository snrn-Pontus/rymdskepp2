package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.NameTagComponent;
import se.snrn.rymdskepp.components.TextureComponent;
import se.snrn.rymdskepp.components.TransformComponent;

import static se.snrn.rymdskepp.Shared.*;

public class RenderingSystem extends SortedIteratingSystem {

    private Batch batch;
    private Array<Entity> renderQueue;
    private OrthographicCamera cam;
    private BitmapFont bitmapFont;

    private ShapeRenderer shapeRenderer;

    public RenderingSystem(Batch batch) {
        super(Family.all(
                TransformComponent.class, TextureComponent.class).get(),
                (entityA, entityB) -> (int) Math.signum(Mappers.transformMapper.get(entityB).pos.z -
                        Mappers.transformMapper.get(entityA).pos.z));
        shapeRenderer = new ShapeRenderer();


        renderQueue = new Array<>();


        this.batch = batch;

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH / 2, FRUSTUM_HEIGHT / 2, 0);

        bitmapFont = new BitmapFont();
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(1 * PIXELS_TO_METRES, 1 * PIXELS_TO_METRES);
//        bitmapFont.getData().setScale(0.1f, 0.1f);
        bitmapFont.setUseIntegerPositions(false);

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
            NameTagComponent tag = Mappers.nameTagMapper.get(entity);

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

            if (tag != null && tag.getName().length() > 0) {
                bitmapFont.draw(batch, tag.getName(), t.pos.x, t.pos.y + 1f);
            }
        }

        batch.end();


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
