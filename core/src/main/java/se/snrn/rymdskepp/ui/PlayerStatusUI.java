package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PlayerStatusUI extends Table {
    public PlayerStatusUI(Skin skin) {
        super(skin);

        setFillParent(true);


        add(new Label("Hej", skin)).expand().top().left();
        add(new Label("Hej", skin)).expand().top().right();
        row();
        add(new Label("Hej", skin)).expand().bottom().left();
        add(new Label("Hej", skin)).expand().bottom().right();
    }

}
