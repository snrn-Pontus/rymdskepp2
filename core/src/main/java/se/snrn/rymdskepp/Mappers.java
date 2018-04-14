package se.snrn.rymdskepp;

import com.badlogic.ashley.core.ComponentMapper;
import se.snrn.rymdskepp.components.*;

import java.util.BitSet;

public class Mappers {
    public static ComponentMapper<MovementComponent> movementMapper = ComponentMapper.getFor(MovementComponent.class);
    public static ComponentMapper<NetworkedComponent> networkedMapper = ComponentMapper.getFor(NetworkedComponent.class);
    public static ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);
    public static ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
    public static ComponentMapper<NameTagComponent> nameTagMapper = ComponentMapper.getFor(NameTagComponent.class);
}
