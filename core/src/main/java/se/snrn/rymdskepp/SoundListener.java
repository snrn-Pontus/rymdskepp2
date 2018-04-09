package se.snrn.rymdskepp;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;

public class SoundListener implements Listener<SoundEnum> {


    @Override
    public void receive(Signal<SoundEnum> signal, SoundEnum soundEnum) {
        System.out.println(soundEnum);
    }
}
