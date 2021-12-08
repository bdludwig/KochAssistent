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
        items.add(o);
    }

    public boolean removeItem(KochAssistentObject o) {
        int i = 0;

        while ((i < items.size()) && (items.get(i).id().equals(o.id()))) {
            System.out.println(items.get(i).id() + " is not " + o.id());
            i++;
        }

        if (i == items.size()) return false;
        else {
            items.remove(i);
            return true;
        }
    }

    public ArrayList<KochAssistentObject> getItems() {
        return items;
    }

    public ParameterSet factsToProlog() {
        ParameterSet p = new ParameterSet();

        for (KochAssistentObject o : items) {
            p = o.factsToProlog(p);
        }

        return p;
    }
}
