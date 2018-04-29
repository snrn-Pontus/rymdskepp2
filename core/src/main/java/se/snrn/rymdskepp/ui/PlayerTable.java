package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import se.snrn.rymdskepp.components.PlayerComponent;

public class PlayerTable extends Table {


    public PlayerTable(Skin skin, NinePatchDrawable ninePatchDrawable, PlayerComponent playerComponent, int align) {
        super(skin);

        setBackground(ninePatchDrawable);
        add(new Label("Id: "+playerComponent.getId(), skin));
        row();
        add(new Label("Score: "+playerComponent.getScore(), skin));
    }
}
