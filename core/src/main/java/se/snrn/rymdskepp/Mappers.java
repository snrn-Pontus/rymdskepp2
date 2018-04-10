package se.snrn.rymdskepp;

import com.badlogic.ashley.core.ComponentMapper;
import se.snrn.rymdskepp.components.*;

public class Mappers {
    public static ComponentMapper<MovementComponent> movementMapper = ComponentMapper.getFor(MovementComponent.class);
    public static ComponentMapper<NetworkedComponent> networkedMapper = ComponentMapper.getFor(NetworkedComponent.class);
    public static ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);
    public static ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);


}
