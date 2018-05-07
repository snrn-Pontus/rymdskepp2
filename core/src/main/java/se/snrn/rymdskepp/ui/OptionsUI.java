package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


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

        CheckBox checkBox = new CheckBox("Debug", skin);

        checkBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                prefs.putBoolean("debug", checkBox.isChecked());
                prefs.flush();
                getStage().setDebugAll(prefs.getBoolean("debug"));
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
        row();
        add(checkBox);
    }


}
