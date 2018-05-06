package se.snrn.rymdskepp.server.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

import java.util.ArrayList;

public class WeaponComponent implements Component, Poolable {
    public TextureRegion region;
    private ArrayList<Vector2> canons;
    private Vector2 currentCanon;
    private int index = 0;
    private int shipType;

    public WeaponComponent(TextureRegion region) {
        this.region = region;
    }

    public WeaponComponent() {
    }

    @Override
    public void reset() {
        this.region = null;
    }

    public void setCanons(ArrayList<Vector2> canons) {
        this.canons = canons;
    }

    public ArrayList<Vector2> getCanons() {
        return canons;
    }

    public Vector2 getCurrentCanon() {
        index++;
        if(index > canons.size()-1){
            index = 0;
        }
        return canons.get(index);
    }

    public void setShipType(int shipType) {
        this.shipType = shipType;
    }

    public int getShipType() {
        return shipType;
    }
}
