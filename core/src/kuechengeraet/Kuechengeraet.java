package kuechengeraet;

import main.KochAssistentObject;
import prolog.ParameterSet;

abstract public class Kuechengeraet extends KochAssistentObject {
    public Kuechengeraet() {
        super();
    }

    public ParameterSet factsToProlog(ParameterSet p) {
        // Klassenname des Geräts und name des Geräts werden dem Parameterset hinzugefügt.
        return p.add(bezeichner() + "(" + id() + ")" );
    }
}
