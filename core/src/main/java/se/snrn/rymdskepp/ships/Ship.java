package se.snrn.rymdskepp.ships;

import com.badlogic.gdx.utils.JsonValue;

public class Ship {
    private final String name;
    private final String sprite;
    private final String bullets;
    private final String creator;
    private int id;

    public Ship(JsonValue ship) {
        name = ship.getString("name");
        sprite = ship.getString("sprite");
        bullets = ship.getString("bullets");
        creator = ship.getString("creator");
        id = ship.getInt("id");
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
}
