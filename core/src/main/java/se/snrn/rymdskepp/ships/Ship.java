package se.snrn.rymdskepp.ships;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.JsonValue;

import static se.snrn.rymdskepp.Shared.PIXELS_TO_METRES;

public class Ship {
    private final String name;
    private final String sprite;
    private final String bullets;
    private final String creator;
    private int id;
    private float offSetX = 0;
    private float offSetY = 0;
    private Color coneLight = Color.WHITE;
    private Color pointLight = Color.WHITE;
    private Color bulletLight = Color.WHITE;

    public Ship(JsonValue ship) {
        name = ship.getString("name");
        sprite = ship.getString("sprite");
        bullets = ship.getString("bullets");
        creator = ship.getString("creator");
        id = ship.getInt("id");

        JsonValue exhaust = ship.get("exhaust");
        JsonValue lights = ship.get("lights");


        if (exhaust != null) {
            offSetX = exhaust.getInt("x") * PIXELS_TO_METRES;
            offSetY = exhaust.getInt("y") * PIXELS_TO_METRES;
        }

        if (lights != null) {
            coneLight = new Color(Color.valueOf(lights.getString("coneLight")));
            pointLight = new Color(Color.valueOf(lights.getString("pointLight")));
            bulletLight = new Color(Color.valueOf(lights.getString("bulletLight")));
        }
    }

    public float getOffSetX() {
        return offSetX;
    }

    public float getOffSetY() {
        return offSetY;
    }

    public String getName() {
        return name;
    }

    public String getSprite() {
        return sprite;
    }

    public String getBullets() {
        return bullets;
    }

    public String getCreator() {
        return creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getConeLight() {
        return coneLight;
    }

    public Color getPointLight() {
        return pointLight;
    }

    public Color getBulletLight() {
        return bulletLight;
    }
}
