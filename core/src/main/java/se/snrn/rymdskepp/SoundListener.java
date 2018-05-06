package se.snrn.rymdskepp;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;

public class SoundListener implements Listener<SoundEnum> {

    private Sound explosion;
    private Sound shoot;

    private float volume;

    public SoundListener() {

        volume = 1f;

        Preferences prefs = Gdx.app.getPreferences("settings");
        if (prefs.contains("volume")) {
            volume = prefs.getFloat("volume");
        } else if (Gdx.app.getType() == Application.ApplicationType.WebGL) {
            volume = 0.5f;
        }

        explosion = Rymdskepp.manager.get(Assets.explosion);
        shoot = Rymdskepp.manager.get(Assets.shoot);

    }

    @Override
    public void receive(Signal<SoundEnum> signal, SoundEnum soundEnum) {
        switch (soundEnum) {
            case SHOOT:
                shoot.play(volume);
                break;
            case EXPLODE:
                explosion.play(volume);
                break;
        }
    }
}
