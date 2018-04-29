package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.LaserComponent;
import se.snrn.rymdskepp.components.TransformComponent;

import static se.snrn.rymdskepp.Shared.PIXELS_TO_METRES;

public class LaserRenderingSystem extends IteratingSystem {

    private final Color color;
    private final OrthographicCamera cam;
    private final Array<Entity> renderQueue;
    private final Color colorOverlay;
    private Batch batch;

    public LaserRenderingSystem(Batch batch, OrthographicCamera cam) {
        super(Family.all(
                LaserComponent.class, TransformComponent.class).get());
        this.batch = batch;
        color = new Color(Color.GREEN);
        colorOverlay = new Color(Color.WHITE);
        this.cam = cam;


        renderQueue = new Array<>();


        this.batch = batch;

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Set Blending to addition; {GL10.GL_SRC_ALPHA, GL10.GL_ONE}
        // Adjust alpha of beam && glow color (decay effect)
        // For {x = start, mid, end sprite}
        // Set color to glow color
        // Draw x-background sprite
        // Set color to beam color
        // Draw x-overlay sprite
        // Set color to default
        // Draw overlay animation from the center of the start sprite to the center of the end sprite.

        batch.setProjectionMatrix(cam.combined);
        cam.update();

        batch.setBlendFunction(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE);


        batch.begin();


        for (Entity entity : renderQueue) {
            LaserComponent laserComponent = Mappers.laserMapper.get(entity);




            TransformComponent t = Mappers.transformMapper.get(entity);

            float width = laserComponent.getStartBase().getRegionWidth();
            float height = laserComponent.getStartBase().getRegionHeight();
            float originX = width * 0.5f;
            float originY = height * 0.5f;

            batch.setColor(color);

            batch.draw(laserComponent.getStartBase(),
                    t.pos.x - originX, t.pos.y - originY,
                    originX, originY,
                    width, height,
                    t.scale.x * PIXELS_TO_METRES,
                    t.scale.y * PIXELS_TO_METRES,
                    MathUtils.radiansToDegrees * t.rotation);

            batch.draw(laserComponent.getMiddleBase(),
                    (t.pos.x - originX), (t.pos.y - originY) + 2,
                    originX, originY,
                    width, height,
                    t.scale.x * PIXELS_TO_METRES,
                    t.scale.y * PIXELS_TO_METRES,
                    MathUtils.radiansToDegrees * t.rotation);

            batch.setColor(colorOverlay);


            batch.draw(laserComponent.getStartOverlay(),
                    t.pos.x - originX, t.pos.y - originY,
                    originX, originY,
                    width, height,
                    t.scale.x * PIXELS_TO_METRES,
                    t.scale.y * PIXELS_TO_METRES,
                    MathUtils.radiansToDegrees * t.rotation);

            batch.draw(laserComponent.getMiddleOverlay(),
                    (t.pos.x - originX), (t.pos.y - originY) + 2,
                    originX, originY,
                    width, height,
                    t.scale.x * PIXELS_TO_METRES,
                    t.scale.y * PIXELS_TO_METRES,
                    MathUtils.radiansToDegrees * t.rotation);

        }

        batch.end();

        batch.setColor(Color.WHITE);

        renderQueue.clear();

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }
}
