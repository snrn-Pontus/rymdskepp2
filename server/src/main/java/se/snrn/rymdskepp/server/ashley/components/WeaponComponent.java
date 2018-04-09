package se.snrn.rymdskepp.server.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class WeaponComponent implements Component, Poolable {
    public TextureRegion region;

    public WeaponComponent(TextureRegion region) {
        this.region = region;
    }

    @Override
    public void reset() {
        this.region = null;
    }
}
