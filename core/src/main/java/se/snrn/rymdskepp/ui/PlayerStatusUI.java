package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

public class PlayerStatusUI extends Table {
    public PlayerStatusUI(Skin skin) {
        super(skin);

        setFillParent(true);

        NinePatch ninePatch = new NinePatch(new Texture(Gdx.files.internal("ui/window.png")), 8, 8, 8, 8);

        NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(ninePatch);

        add(new PlayerTable(skin, ninePatchDrawable, Align.topLeft)).expand().align(Align.topLeft).fill().width(Value.percentWidth(0.25f)).height(Value.percentHeight(0.25f));
        add(new PlayerTable(skin, ninePatchDrawable, Align.topRight)).expand().align(Align.topRight).fill().width(Value.percentWidth(0.25f)).height(Value.percentHeight(0.25f));
        row();
        add(new PlayerTable(skin, ninePatchDrawable, Align.bottomLeft)).expand().align(Align.bottomLeft).fill().width(Value.percentWidth(0.25f)).height(Value.percentHeight(0.25f));
        add(new PlayerTable(skin, ninePatchDrawable, Align.bottomRight)).expand().align(Align.bottomRight).fill().width(Value.percentWidth(0.25f)).height(Value.percentHeight(0.25f));


    }

}
