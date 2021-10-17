package moebel;

import main.KochAssistentObject;
import prolog.ParameterSet;

abstract public class Moebel extends KochAssistentObject {
    public Moebel() {
        super();
    }

    public ParameterSet factsToProlog(ParameterSet p) {
        // Klassenname des Geräts und name des Geräts werden dem Parameterset hinzugefügt.
        return p.add(bezeichner() + "(" + id()+ ")" );
    }
    public abstract void perform();
}
