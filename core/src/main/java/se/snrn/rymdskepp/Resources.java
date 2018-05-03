package se.snrn.rymdskepp;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Resources {
    private static TextureAtlas textureAtlas;

    public static TextureAtlas.AtlasRegion getTexture(String string) {
        if (textureAtlas == null){
            textureAtlas = Rymdskepp.manager.get(Assets.atlas);
        }
        return textureAtlas.findRegion(string);
    }

    public static TextureRegion getTextureRegion(String string) {
        if (textureAtlas == null){
            textureAtlas = Rymdskepp.manager.get(Assets.atlas);
        }
        return textureAtlas.findRegion(string);
    }

    public static Array<TextureAtlas.AtlasRegion> getTextures(String animationName) {
        if (textureAtlas == null){
            textureAtlas = Rymdskepp.manager.get(Assets.atlas);
        }
        return textureAtlas.findRegions(animationName);
    }
}
