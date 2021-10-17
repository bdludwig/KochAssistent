package raum;

import elektrogeraet.Ofen;
import main.KochAssistentObject;

import java.util.ArrayList;

public class Kueche {
    private ArrayList<KochAssistentObject> items;

    public Kueche() {
        items = new ArrayList<KochAssistentObject>();
    }

    public void addItem(KochAssistentObject o) {
        items.add(o);
    }

    public ArrayList<KochAssistentObject> getItems() {
        return items;
    }

    public void init() {
        items.add(new Ofen());
    }
}
