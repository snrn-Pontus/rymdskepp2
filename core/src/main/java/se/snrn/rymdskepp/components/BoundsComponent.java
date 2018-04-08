package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class BoundsComponent implements Component {
    public Rectangle bounds;

    public BoundsComponent() {
       bounds = new Rectangle();
    }
}
