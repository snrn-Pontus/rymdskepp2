package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.SharedMappers;
import se.snrn.rymdskepp.components.ParallaxComponent;
import se.snrn.rymdskepp.components.TextureComponent;
import se.snrn.rymdskepp.components.TransformComponent;

import static se.snrn.rymdskepp.Shared.*;

public class BackgroundSystem extends SortedIteratingSystem {
    private Batch batch;
    private Array<Entity> renderQueue;
    private OrthographicCamera cam;

    public BackgroundSystem(Batch batch, OrthographicCamera cam) {
        super(Family.all(ParallaxComponent.class, TransformComponent.class, TextureComponent.class).get(),
                (entityA, entityB) -> (int) Math.signum(SharedMappers.transformMapper.get(entityB).pos.z -
                        SharedMappers.transformMapper.get(entityA).pos.z));
        this.cam = cam;
        renderQueue = new Array<>();

        this.batch = batch;



    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);


        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.setBlendFunction(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE);
        batch.begin();
//        batch.draw(background,0,0,WIDTH,HEIGHT);

        for (Entity entity : renderQueue) {
            TextureComponent textureComponent = Mappers.textureMapper.get(entity);
            ParallaxComponent parallaxComponent = Mappers.parallaxMapper.get(entity);


            // TODO NULL POINTER!?!?!?!


            TransformComponent transformComponent = SharedMappers.transformMapper.get(entity);

            float width = textureComponent.region.getRegionWidth();
            float height = textureComponent.region.getRegionHeight();
            float originX = width * 0.5f;
            float originY = height * 0.5f;


            batch.draw(textureComponent.region,
                    transformComponent.pos.x - originX, transformComponent.pos.y - originY,
                    originX, originY,
                    width, height,
                    transformComponent.scale.x * PIXELS_TO_METRES,
                    transformComponent.scale.y * PIXELS_TO_METRES,
                    MathUtils.radiansToDegrees * transformComponent.rotation);


        }
        batch.end();


        renderQueue.clear();
    }
}




