package se.snrn.rymdskepp;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundListener implements Listener<SoundEnum> {

    Sound explosion;
    Sound shoot;


    public SoundListener() {
        explosion = Gdx.audio.newSound(Gdx.files.getFileHandle("sounds/explosion.wav", Files.FileType.Internal));
        shoot = Gdx.audio.newSound(Gdx.files.getFileHandle("sounds/shoot.wav", Files.FileType.Internal));
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
