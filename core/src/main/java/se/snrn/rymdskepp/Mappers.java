package se.snrn.rymdskepp;

import com.badlogic.ashley.core.ComponentMapper;
import se.snrn.rymdskepp.components.*;

public class Mappers {
    public static ComponentMapper<AsteroidComponent> asteroidMapper = ComponentMapper.getFor(AsteroidComponent.class);
    public static ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(BoundsComponent.class);
    public static ComponentMapper<BulletComponent> bulletMapper = ComponentMapper.getFor(BulletComponent.class);
    public static ComponentMapper<ControlledComponent> controlledMapper = ComponentMapper.getFor(ControlledComponent.class);
    public static ComponentMapper<CircleBoundsComponent> circleBoundsComponentMapper = ComponentMapper.getFor(CircleBoundsComponent.class);
    public static ComponentMapper<EnemyComponent> enemyMapper = ComponentMapper.getFor(EnemyComponent.class);
    public static ComponentMapper<MovementComponent> movementMapper = ComponentMapper.getFor(MovementComponent.class);
    public static ComponentMapper<NetworkedComponent> networkedMapper = ComponentMapper.getFor(NetworkedComponent.class);
    public static ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);
    public static ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
    public static ComponentMapper<WeaponComponent> weaponMapper = ComponentMapper.getFor(WeaponComponent.class);
    public static ComponentMapper<WrapAroundComponent> wrapAroundMapper = ComponentMapper.getFor(WrapAroundComponent.class);

}
