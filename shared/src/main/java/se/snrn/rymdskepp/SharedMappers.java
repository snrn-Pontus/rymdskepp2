package se.snrn.rymdskepp;

import com.badlogic.ashley.core.ComponentMapper;
import se.snrn.rymdskepp.components.ControlledComponent;
import se.snrn.rymdskepp.components.TransformComponent;

public class SharedMappers {

    public static ComponentMapper<TransformComponent> transformMapper = ComponentMapper.getFor(TransformComponent.class);
    public static ComponentMapper<ControlledComponent> controlledMapper = ComponentMapper.getFor(ControlledComponent.class);
}
