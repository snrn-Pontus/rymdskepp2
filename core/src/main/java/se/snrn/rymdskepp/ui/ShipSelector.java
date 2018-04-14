package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import java.util.ArrayList;

public class ShipSelector extends Table {
    private ArrayList<SpriteDrawable> shipImages;

    private SpriteDrawable selectedShip;
    private Image image;
    public ShipSelector(Skin skin) {
        super(skin);

        shipImages = new ArrayList<>();

        TextButton left = new TextButton("<", skin);
        left.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectPreviousShip();
            }
        });
        add(left);



        image = new Image();

        for (int i = 0; i < 3; i++) {
            shipImages.add(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ship" + i + ".png")))));
        }

        selectedShip = shipImages.get(0);

        image.setDrawable(selectedShip);


        Table table = new Table(skin);
        table.setBackground("select-box-pressed-c");
        table.add(image);

        add(table).padRight(10).padLeft(10);


        TextButton right = new TextButton(">", skin);
        right.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectNextShip();
            }
        });
        add(right);
    }

    private void selectNextShip() {
        int selectedShipPosition = shipImages.indexOf(selectedShip);
        int newShipPosition;
        if(selectedShipPosition == shipImages.size()-1){
            newShipPosition = 0;
        } else {
            newShipPosition = selectedShipPosition +1;
        }
        selectedShip = shipImages.get(newShipPosition);
        image.setDrawable(selectedShip);
    }

    private void selectPreviousShip() {
        int selectedShipPosition = shipImages.indexOf(selectedShip);
        int newShipPosition;
        if(selectedShipPosition == 0){
            newShipPosition = shipImages.size()-1;
        } else {
            newShipPosition = selectedShipPosition -1;
        }
        selectedShip = shipImages.get(newShipPosition);
        image.setDrawable(selectedShip);
    }


}
