package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class NameTagComponent implements Component,Pool.Poolable {

    private String name;

    public NameTagComponent() {
    }

    @Override
    public void reset() {
        name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
