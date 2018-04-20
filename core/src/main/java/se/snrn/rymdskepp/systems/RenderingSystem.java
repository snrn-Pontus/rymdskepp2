package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.NameTagComponent;
import se.snrn.rymdskepp.components.ParallaxComponent;
import se.snrn.rymdskepp.components.TextureComponent;
import se.snrn.rymdskepp.components.TransformComponent;

import static se.snrn.rymdskepp.Shared.*;

public class RenderingSystem extends SortedIteratingSystem {

    private Batch batch;
    private Array<Entity> renderQueue;
    private OrthographicCamera cam;
    private BitmapFont bitmapFont;


    public RenderingSystem(Batch batch, OrthographicCamera cam) {
        super(Family.all(
                TransformComponent.class, TextureComponent.class).exclude(ParallaxComponent.class).get(),
                (entityA, entityB) -> (int) Math.signum(Mappers.transformMapper.get(entityB).pos.z -
                        Mappers.transformMapper.get(entityA).pos.z));
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
            TextureComponent textureComponent = Mappers.textureMapper.get(entity);


            // TODO NULL POINTER!?!?!?!
            if (textureComponent != null && textureComponent.region != null) {


                TransformComponent t = Mappers.transformMapper.get(entity);
                NameTagComponent tag = Mappers.nameTagMapper.get(entity);

                float width = textureComponent.region.getRegionWidth();
                float height = textureComponent.region.getRegionHeight();
                float originX = width * 0.5f;
                float originY = height * 0.5f;



                batch.draw(textureComponent.region,
                        t.pos.x - originX, t.pos.y - originY,
                        originX, originY,
                        width, height,
                        t.scale.x * PIXELS_TO_METRES,
                        t.scale.y * PIXELS_TO_METRES,
                        MathUtils.radiansToDegrees * t.rotation);

                if (tag != null && tag.getName().length() > 0) {
                    bitmapFont.draw(batch, tag.getName(), t.pos.x, t.pos.y + 1f);
                }
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
