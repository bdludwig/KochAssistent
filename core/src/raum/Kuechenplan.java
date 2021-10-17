package raum;

import main.KochAssistentObject;

import java.util.HashMap;
import java.util.Set;

public class Kuechenplan {
    private HashMap<String,Konfiguration> plan;

    public Kuechenplan() {
        plan = new HashMap<String,Konfiguration>();
    }

    public void neuerGegenstand(KochAssistentObject o, int x, int y, int w, int h) {
        plan.put(o.id(), new Konfiguration(x, y, w, h));
    }

    public Konfiguration konfigurationVon(String id) {
        return plan.get(id);
    }
}
