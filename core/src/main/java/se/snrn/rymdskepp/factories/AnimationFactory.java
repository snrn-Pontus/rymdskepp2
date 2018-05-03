package se.snrn.rymdskepp.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import se.snrn.rymdskepp.Resources;

public class AnimationFactory {
    public static Animation<TextureAtlas.AtlasRegion> createAnimation(String animationName) {
        Array<TextureAtlas.AtlasRegion> textures = Resources.getTextures(animationName);
        return new Animation<>(1f / 16f, textures, Animation.PlayMode.LOOP);
    }
}
