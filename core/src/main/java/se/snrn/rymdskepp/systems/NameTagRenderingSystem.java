package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.NameTagComponent;
import se.snrn.rymdskepp.components.TransformComponent;

import static se.snrn.rymdskepp.Shared.PIXELS_TO_METRES;

public class NameTagRenderingSystem extends IteratingSystem {
    private final Camera cam;
    private final Batch batch;
    private final Array<Entity> renderQueue;

    private BitmapFont bitmapFont;

    public NameTagRenderingSystem(Batch batch, OrthographicCamera cam) {
        super(Family.all(
                NameTagComponent.class, TransformComponent.class).get());

        this.cam = cam;


        renderQueue = new Array<>();


        this.batch = batch;


        bitmapFont = new BitmapFont();
        bitmapFont.setColor(Color.WHITE);
        bitmapFont.getData().setScale(1 * PIXELS_TO_METRES, 1 * PIXELS_TO_METRES);
        bitmapFont.setUseIntegerPositions(false);
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        batch.setProjectionMatrix(cam.combined);
        cam.update();
        batch.begin();


        for (Entity entity : renderQueue) {
            TransformComponent t = Mappers.transformMapper.get(entity);
            NameTagComponent tag = Mappers.nameTagMapper.get(entity);
            if (tag != null && tag.getName().length() > 0) {
                bitmapFont.draw(batch, tag.getName(), t.pos.x, t.pos.y + 1f);
            }
        }

        batch.end();


        renderQueue.clear();
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);

    }
}
