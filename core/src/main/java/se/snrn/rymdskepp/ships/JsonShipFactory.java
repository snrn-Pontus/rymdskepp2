package se.snrn.rymdskepp.ships;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class JsonShipFactory {
    private ArrayList<Ship> ships;

    public JsonShipFactory() {
        ships = new ArrayList<>();
        JsonReader jsonReader = new JsonReader();
        JsonValue shipsJson = jsonReader.parse(Gdx.files.getFileHandle("ships/ships.json", Files.FileType.Internal));
        for (JsonValue ship : shipsJson) {
            ships.add(new Ship(ship));
        }
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }
}
