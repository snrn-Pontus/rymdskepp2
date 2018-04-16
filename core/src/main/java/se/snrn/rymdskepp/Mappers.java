package se.snrn.rymdskepp;

import com.badlogic.ashley.core.ComponentMapper;
import se.snrn.rymdskepp.components.*;

public class Mappers {
    public static ComponentMapper<ClientNetworkedComponent> networkedMapper = ComponentMapper.getFor(ClientNetworkedComponent.class);
    public static ComponentMapper<TextureComponent> textureMapper = ComponentMapper.getFor(TextureComponent.class);
    public static ComponentMapper<NameTagComponent> nameTagMapper = ComponentMapper.getFor(NameTagComponent.class);
}
