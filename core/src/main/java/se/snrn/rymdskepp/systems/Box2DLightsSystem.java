package se.snrn.rymdskepp.systems;

import box2dLight.RayHandler;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import se.snrn.rymdskepp.Mappers;
import se.snrn.rymdskepp.components.ChainLightComponent;
import se.snrn.rymdskepp.components.ConeLightComponent;
import se.snrn.rymdskepp.components.LightComponent;
import se.snrn.rymdskepp.components.TransformComponent;

import static com.badlogic.gdx.math.MathUtils.radDeg;

public class Box2DLightsSystem extends IteratingSystem {


    private RayHandler rayHandler;
    private OrthographicCamera camera;
    private Batch batch;
    private World world;
    private Array<Entity> lightsQueue;

    public Box2DLightsSystem(World world, RayHandler rayHandler, OrthographicCamera camera, Batch batch) {
        super(Family.all(TransformComponent.class).one(LightComponent.class, ConeLightComponent.class, ChainLightComponent.class).get());
        this.world = world;
        this.rayHandler = rayHandler;
        this.camera = camera;
        this.batch = batch;
        this.rayHandler.setWorld(world);
        this.lightsQueue = new Array<>();
        this.rayHandler.setCombinedMatrix(camera);
        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);

        Color color = new Color();
        color.set(0,0,0.1f,0.5f);
        this.rayHandler.setAmbientLight(color);
        this.rayHandler.setBlurNum(3);


        }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        camera.update();

        rayHandler.setCombinedMatrix(camera);

        world.step(1 / 60f, 6, 2);

        for (Entity entity : lightsQueue) {
            LightComponent lightComponent = Mappers.lightMapper.get(entity);
            ConeLightComponent coneLightComponent = Mappers.coneLightMapper.get(entity);
            ChainLightComponent chainLightComponent = Mappers.chainLightMapper.get(entity);
            TransformComponent t = Mappers.transformMapper.get(entity);


            Vector2 vector2 = new Vector2(t.pos.x, t.pos.y);

            if (lightComponent != null) {
                lightComponent.setLightPosition(vector2);
                lightComponent.update();
            } else if (coneLightComponent != null) {
                coneLightComponent.setLightPosition(vector2);
                coneLightComponent.setDirection((radDeg * t.rotation) + 90);
                coneLightComponent.update();
            } else if(chainLightComponent != null) {
                chainLightComponent.setLightPosition(vector2);
                chainLightComponent.update();
            }

        }
        rayHandler.updateAndRender();

        lightsQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        lightsQueue.add(entity);
    }
}