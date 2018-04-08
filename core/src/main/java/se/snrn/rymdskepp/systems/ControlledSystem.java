package se.snrn.rymdskepp.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Polygon;
import se.snrn.rymdskepp.components.*;

import static se.snrn.rymdskepp.systems.RenderingSystem.PIXELS_TO_METRES;

public class ControlledSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;

    private ComponentMapper<TransformComponent> transformM;

    public ControlledSystem() {
        transformM = ComponentMapper.getFor(TransformComponent.class);
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(ControlledComponent.class,MovementComponent.class).get());

    }

    public void setYVelocity(float v) {
        MovementComponent movementComponent = entities.get(0).getComponent(MovementComponent.class);
        movementComponent.accel.y = v;
    }

    public void setXVelocity(float v) {
        MovementComponent movementComponent = entities.get(0).getComponent(MovementComponent.class);
        movementComponent.velocity.x = v;
    }

    public void setTurning(float angle) {
        MovementComponent movementComponent = entities.get(0).getComponent(MovementComponent.class);
        movementComponent.rotation = angle;
    }
    public void shoot(Entity entity) {

        TransformComponent t = transformM.get(entity);

        WeaponComponent weaponComponent = entities.get(0).getComponent(WeaponComponent.class);
        Entity bullet = getEngine().createEntity();
        TransformComponent transformComponent = getEngine().createComponent(TransformComponent.class);
        transformComponent.pos.set(t.pos);
        transformComponent.rotation = t.rotation;
        bullet.add(transformComponent);
        BoundsComponent boundsComponent = getEngine().createComponent(BoundsComponent.class);
        boundsComponent.bounds.width = weaponComponent.region.getRegionWidth()*PIXELS_TO_METRES;
        boundsComponent.bounds.height = weaponComponent.region.getRegionHeight()*PIXELS_TO_METRES;
        boundsComponent.bounds.setPosition(t.pos.x-0.5f,t.pos.y-0.5f);
        Polygon polygon = new Polygon();
        float[] vertices = {t.pos.x,t.pos.y,0,1,0,1,0};
        polygon.setVertices(vertices);

        bullet.add(boundsComponent);
        bullet.add(new TextureComponent(weaponComponent.region));

        MovementComponent movementComponent = getEngine().createComponent(MovementComponent.class);
//        Vector2 vector2 = new Vector2(0,10).rotate(MathUtils.degRad * t.rotation);

//        movementComponent.accel.set(0,10);
        movementComponent.velocity.set(0,10).setAngleRad(t.rotation).rotate90(1);

        bullet.add(movementComponent);
//        bullet.add(new WrapAroundComponent());
        bullet.add(new BulletComponent());
        bullet.add(new WrapAroundComponent());

        getEngine().addEntity(bullet);
    }
}
