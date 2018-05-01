package se.snrn.rymdskepp;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonValue;

import static net.dermetfan.gdx.assets.AnnotationAssetManager.Asset;

public class Assets {
    @Asset(Texture.class)
    public static final String
            background = "background.png",
            nebula = "nebula.png",
            star = "star.png",
            bullet0 = "ships/bullet0.png",
            bullet = "bullet.png",
            ship0 = "ships/ship0.png",
            ship1 = "ships/ship1.png",
            ship1_acceleration = "ships/ship1_acceleration.png",
            ship2 = "ships/ship2.png",
            ship3 = "ships/ship3.png",
            ship4 = "ships/ship4.png",
            ship5 = "ships/ship5.png",
            ship5_acceleration = "ships/ship5_acceleration.png",
            window = "ui/window.png",
            laser_middle = "laser/laser_middle.png",
            laser_middle_overlay = "laser/laser_middle_overlay.png",
            laser_start = "laser/laser_start.png",
            laser_start_overlay = "laser/laser_start_overlay.png";
    @Asset(Skin.class)
    public static final String
            skin = "skin/quantum-horizon-ui.json";
//    @Asset(JsonValue.class)
//    public static final String
//            ships = "ships/ships.json";
    @Asset(Sound.class)
    public static final String
            explosion = "sounds/explosion.wav",
            shoot = "sounds/shoot.wav";
}
