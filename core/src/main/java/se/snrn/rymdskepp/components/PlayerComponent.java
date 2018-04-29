package se.snrn.rymdskepp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {

     private int score = 0;
     private long id;

    public PlayerComponent(int score, long id) {
        this.score = score;
        this.id = id;
    }

    public PlayerComponent() {
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void reset() {
        score = 0;
        id = 0;
    }
}
