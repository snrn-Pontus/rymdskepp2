package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TextureComponent implements Component, Poolable {
    public TextureRegion region;

    public TextureRegion getRegion() {
        return region;
    }

    public void setRegion(TextureRegion region) {
        this.region = region;
    }

    public TextureComponent() {
    }

    public TextureComponent(TextureRegion texture) {
        this.region = texture;
    }

    @Override
    public void reset() {
        this.region = null;
    }
}
