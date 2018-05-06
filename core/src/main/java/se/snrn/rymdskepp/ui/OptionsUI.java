package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class OptionsUI extends Table {
    private Preferences prefs;

    public OptionsUI(Skin skin) {
        super(skin);

        prefs = Gdx.app.getPreferences("settings");

        Slider slider = new Slider(0f,1f,0.01f,false,skin);

        slider.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {

                prefs.putFloat("volume",slider.getValue());
                prefs.flush();
            }
        });

        if(prefs.contains("volume")){
            slider.setValue(prefs.getFloat("volume"));
        } else {
            slider.setValue(0.5f);
        }

        add(new Label("Volume",skin));
        row();
        add(slider);
    }


}
