package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class LaserComponent implements Component, Poolable {

    private TextureRegion startBase;
    private TextureRegion startOverlay;
    private TextureRegion middleBase;
    private TextureRegion middleOverlay;
    private TextureRegion endBase;
    private TextureRegion endOverlay;

    public LaserComponent() {
    }

    public LaserComponent(TextureRegion startBase, TextureRegion startOverlay, TextureRegion middleBase, TextureRegion middleOverlay, TextureRegion endBase, TextureRegion endOverlay) {
        this.startBase = startBase;
        this.startOverlay = startOverlay;
        this.middleBase = middleBase;

        this.middleOverlay = middleOverlay;
        this.endBase = endBase;
        this.endOverlay = endOverlay;
    }

    public void setStartBase(TextureRegion startBase) {
        this.startBase = startBase;
    }

    public void setStartOverlay(TextureRegion startOverlay) {
        this.startOverlay = startOverlay;
    }

    public void setMiddleBase(TextureRegion middleBase) {
        this.middleBase = middleBase;
    }

    public void setMiddleOverlay(TextureRegion middleOverlay) {
        this.middleOverlay = middleOverlay;
    }

    public void setEndBase(TextureRegion endBase) {
        this.endBase = endBase;
    }

    public void setEndOverlay(TextureRegion endOverlay) {
        this.endOverlay = endOverlay;
    }

    public TextureRegion getStartBase() {
        return startBase;
    }

    public TextureRegion getStartOverlay() {
        return startOverlay;
    }

    public TextureRegion getMiddleBase() {
        return middleBase;
    }

    public TextureRegion getMiddleOverlay() {
        return middleOverlay;
    }

    public TextureRegion getEndBase() {
        return endBase;
    }

    public TextureRegion getEndOverlay() {
        return endOverlay;
    }

    @Override
    public void reset() {
        startBase = null;
        startOverlay = null;
        middleBase = null;
        middleOverlay = null;
        endBase = null;
        endOverlay = null;
    }
}
