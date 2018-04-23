package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class PlayerTable extends Table {
    public PlayerTable(Skin skin, NinePatchDrawable ninePatchDrawable, int align) {
        super(skin);


        setBackground(ninePatchDrawable);
        add(new Label("Hej", skin));
    }
}
