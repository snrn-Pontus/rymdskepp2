package se.snrn.rymdskepp;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static net.dermetfan.gdx.assets.AnnotationAssetManager.Asset;

public class Assets {
    @Asset(Texture.class)
    public static final String
            background = "background.png",
            nebula = "images/nebula.png",
            star = "images/star.png",
            bullet0 = "images/ships/bullet0.png",
            bullet = "images/bullet.png",
            ship0 = "images/ships/ship0.png",
            ship1 = "images/ships/ship1.png",
            ship1_acceleration = "images/ships/ship1_acceleration.png",
            ship2 = "images/ships/ship2.png",
            ship3 = "images/ships/ship3.png",
            ship4 = "images/ships/ship4.png",
            ship5 = "images/ships/ship5.png",
            ship5_acceleration = "images/ships/ship5_acceleration.png",
            window = "images/ui/window.png",
            laser_middle = "images/laser/laser_middle.png",
            laser_middle_overlay = "images/laser/laser_middle_overlay.png",
            laser_start = "images/laser/laser_start.png",
            laser_start_overlay = "images/laser/laser_start_overlay.png";
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

    @Asset(TextureAtlas.class)
    public static final String atlas = "packed/game.atlas";

}
