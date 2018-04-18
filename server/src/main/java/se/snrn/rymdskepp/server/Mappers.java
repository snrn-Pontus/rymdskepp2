package se.snrn.rymdskepp.server;

import com.badlogic.ashley.core.ComponentMapper;
import se.snrn.rymdskepp.components.ControlledComponent;
import se.snrn.rymdskepp.components.TransformComponent;
import se.snrn.rymdskepp.server.components.*;

public class Mappers {
    public static ComponentMapper<AsteroidComponent> asteroidMapper = ComponentMapper.getFor(se.snrn.rymdskepp.server.components.AsteroidComponent.class);
    public static ComponentMapper<BoundsComponent> boundsMapper = ComponentMapper.getFor(se.snrn.rymdskepp.server.components.BoundsComponent.class);
    public static ComponentMapper<BulletComponent> bulletMapper = ComponentMapper.getFor(se.snrn.rymdskepp.server.components.BulletComponent.class);
    public static ComponentMapper<CircleBoundsComponent> circleBoundsComponentMapper = ComponentMapper.getFor(se.snrn.rymdskepp.server.components.CircleBoundsComponent.class);
    public static ComponentMapper<PlayerComponent> playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static ComponentMapper<MovementComponent> movementMapper = ComponentMapper.getFor(MovementComponent.class);
    public static ComponentMapper<NetworkedComponent> networkedMapper = ComponentMapper.getFor(se.snrn.rymdskepp.server.components.NetworkedComponent.class);
    public static ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
    public static ComponentMapper<WeaponComponent> weaponMapper = ComponentMapper.getFor(WeaponComponent.class);
    public static ComponentMapper<WrapAroundComponent> wrapAroundMapper = ComponentMapper.getFor(WrapAroundComponent.class);
    public static ComponentMapper<ControlledComponent> controlledMapper = ComponentMapper.getFor(ControlledComponent.class);
}
