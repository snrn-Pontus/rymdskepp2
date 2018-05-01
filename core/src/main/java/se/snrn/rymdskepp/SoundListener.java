package se.snrn.rymdskepp;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundListener implements Listener<SoundEnum> {

    private Sound explosion;
    private Sound shoot;


    public SoundListener() {
        explosion = Rymdskepp.manager.get(Assets.explosion);
        shoot = Rymdskepp.manager.get(Assets.shoot);

    }

    @Override
    public void receive(Signal<SoundEnum> signal, SoundEnum soundEnum) {
        switch (soundEnum) {
            case SHOOT:
                shoot.play();
                break;
            case EXPLODE:
                explosion.play();
                break;
        }
    }
}
