package se.snrn.rymdskepp.server.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import se.snrn.rymdskepp.State;
import se.snrn.rymdskepp.server.components.StateComponent;


public class StateSystem extends IteratingSystem {


    ComponentMapper<StateComponent> sm;

    public StateSystem() {
        super(Family.all(StateComponent.class).get());

        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        StateComponent state = sm.get(entity);

        if (state.time > 0.7f && state.get() == State.SHOOT) {
            state.set(State.DEFAULT);
            state.time = 0;
        }
        state.time += deltaTime;

    }
}