package se.snrn.rymdskepp.server.ships;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

import static se.snrn.rymdskepp.Shared.PIXELS_TO_METRES;

public class Ship {
    private final String name;
    private final String creator;
    private int id;
    private float offSetX = 0;
    private float offSetY = 0;
    private Color coneLight = Color.WHITE;
    private Color pointLight = Color.WHITE;
    private Color bulletLight = Color.WHITE;
    ArrayList<Vector2> canonList;
    private String normalAnimation;
    private String accelerationAnimation;
    private String shootAnimation;
    private String particle;
    private String bullet;
    private Object canons;


    public Ship(JsonValue ship) {
        name = ship.getString("name");
        creator = ship.getString("creator");
        id = ship.getInt("id");

        JsonValue exhaust = ship.get("exhaust");
        JsonValue lights = ship.get("lights");
        JsonValue canons = ship.get("canons");

        JsonValue animations = ship.get("animations");
        String folder = ship.getString("folder");

        bullet = folder + "_" + "bullet";

        if (animations != null) {
            normalAnimation = folder + "_" + animations.getString("normal");
            accelerationAnimation = folder + "_" + animations.getString("acceleration");
            shootAnimation = folder + "_" + animations.getString("shoot");
        }

        if (exhaust != null) {
            offSetX = exhaust.getInt("x") * PIXELS_TO_METRES;
            offSetY = exhaust.getInt("y") * PIXELS_TO_METRES;
            particle = folder + "_" + exhaust.getString("particle");
        }

        if (lights != null) {
            coneLight = new Color(Color.valueOf(lights.getString("coneLight")));
            pointLight = new Color(Color.valueOf(lights.getString("pointLight")));
            bulletLight = new Color(Color.valueOf(lights.getString("bulletLight")));
        }
        canonList = new ArrayList<>();

        if (canons != null) {
            System.out.println(canons);
            for (JsonValue canon : canons) {
                canonList.add(new Vector2(canon.getFloat("x"), canon.getFloat("y")));
            }
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

    public String getNormalAnimation() {
        return normalAnimation;
    }

    public void setNormalAnimation(String normalAnimation) {
        this.normalAnimation = normalAnimation;
    }

    public String getAccelerationAnimation() {
        return accelerationAnimation;
    }

    public void setAccelerationAnimation(String accelerationAnimation) {
        this.accelerationAnimation = accelerationAnimation;
    }

    public String getShootAnimation() {
        return shootAnimation;
    }

    public void setShootAnimation(String shootAnimation) {
        this.shootAnimation = shootAnimation;
    }

    public String getParticle() {
        return particle;
    }

    public void setParticle(String particle) {
        this.particle = particle;
    }

    public String getBullet() {
        return bullet;
    }

    public void setBullet(String bullet) {
        this.bullet = bullet;
    }

    public ArrayList<Vector2> getCanons() {
        return canonList;
    }

}
