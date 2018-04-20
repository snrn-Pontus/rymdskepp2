package se.snrn.rymdskepp;

import com.badlogic.ashley.core.ComponentMapper;
import se.snrn.rymdskepp.components.*;

import javax.swing.*;

public class Mappers {
    public static ComponentMapper<ClientNetworkedComponent> networkedMapper = ComponentMapper.getFor(ClientNetworkedComponent.class);
    public static ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);
    public static ComponentMapper<NameTagComponent> nameTagMapper = ComponentMapper.getFor(NameTagComponent.class);
    public static ComponentMapper<ParallaxComponent> parallaxMapper = ComponentMapper.getFor(ParallaxComponent.class);
    public static ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
    public static ComponentMapper<LightComponent> lightMapper = ComponentMapper.getFor(LightComponent.class);
    public static ComponentMapper<ConeLightComponent> coneLightMapper = ComponentMapper.getFor(ConeLightComponent.class);
    public static ComponentMapper<MovementComponent> movementMapper = ComponentMapper.getFor(MovementComponent.class);
}
