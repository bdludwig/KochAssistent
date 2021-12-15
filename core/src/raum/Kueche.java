package raum;

import elektrogeraet.Ofen;
import main.KochAssistentObject;
import prolog.ParameterSet;

import java.util.ArrayList;

public class Kueche {
    private ArrayList<KochAssistentObject> items;

    public Kueche() {
        items = new ArrayList<KochAssistentObject>();
    }

    public void addItem(KochAssistentObject o) {
        if (!items.contains(o)) items.add(o);
    }

    public void removeItem(KochAssistentObject o) {
        items.remove(o);
    }

    public ArrayList<KochAssistentObject> getItems() {
        return items;
    }

    public ParameterSet factsToProlog() {
        ParameterSet p = new ParameterSet();

        System.out.println(this.getClass().getSimpleName() + " factsToProlog: " + items.size());
        for (KochAssistentObject o : items) {
            p = o.factsToProlog(p);
        }

        return p;
    }
}
