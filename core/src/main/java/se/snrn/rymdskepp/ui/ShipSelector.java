package se.snrn.rymdskepp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import se.snrn.rymdskepp.ships.JsonShipFactory;
import se.snrn.rymdskepp.ships.Ship;

import java.util.ArrayList;

public class ShipSelector extends Table {
    private ArrayList<SpriteDrawable> shipImages;
    private ArrayList<Ship> ships;
    private SpriteDrawable selectedShip;
    private Image image;

    public ShipSelector(Skin skin, JsonShipFactory jsonShipFactory) {
        super(skin);

        ships = jsonShipFactory.getShips();
        shipImages = new ArrayList<>();


        TextButton left = new TextButton("<", skin);
        ClickListener leftListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectPreviousShip();
            }
        };
        left.addListener(leftListener);
        add(left);


        image = new Image();


        for (Ship ship : ships) {
            shipImages.add(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("ships/" + ship.getSprite())))));
        }
        selectedShip = shipImages.get(0);

        image.setDrawable(selectedShip);

        Table table = new Table(skin);
        table.setBackground("select-box-pressed-c");
        table.add(image);

        add(table).padRight(10).padLeft(10);


        TextButton right = new TextButton(">", skin);
        ClickListener rightListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectNextShip();
            }
        };
        right.addListener(rightListener);
        add(right);
    }

    private void selectNextShip() {
        int selectedShipPosition = shipImages.indexOf(selectedShip);
        int newShipPosition;
        if (selectedShipPosition == shipImages.size() - 1) {
            newShipPosition = 0;
        } else {
            newShipPosition = selectedShipPosition + 1;
        }
        selectedShip = shipImages.get(newShipPosition);
        image.setDrawable(selectedShip);
    }

    private void selectPreviousShip() {
        int selectedShipPosition = shipImages.indexOf(selectedShip);
        int newShipPosition;
        if (selectedShipPosition == 0) {
            newShipPosition = shipImages.size() - 1;
        } else {
            newShipPosition = selectedShipPosition - 1;
        }
        selectedShip = shipImages.get(newShipPosition);
        image.setDrawable(selectedShip);
    }


    public Ship getSelectedShip() {
        return ships.get(shipImages.indexOf(selectedShip));
    }
}
