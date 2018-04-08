package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WeaponComponent implements Component {
    public TextureRegion region;

    public WeaponComponent(TextureRegion region) {
        this.region = region;
    }
}
