package se.snrn.rymdskepp.server.components;

import com.badlogic.ashley.core.Component;
import se.snrn.rymdskepp.ObjectType;
import se.snrn.rymdskepp.State;

import static com.badlogic.gdx.utils.Pool.Poolable;

public class NetworkedComponent implements Component, Poolable {

    public long id;
    public ObjectType type;
    private State state;

    @Override
    public void reset() {
        id = 0;
        type = null;
        state = State.DEFAULT;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
