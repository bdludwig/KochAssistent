package moebel;

import main.KochAssistentObject;
import prolog.ParameterSet;

import java.util.ArrayList;

abstract public class Moebel extends KochAssistentObject {
    protected ArrayList<KochAssistentObject> storedObjects;

    public Moebel() {
        super();

        storedObjects = new ArrayList<KochAssistentObject>();
    }

    public ParameterSet factsToProlog(ParameterSet p) {
        // Klassenname des Geräts und name des Geräts werden dem Parameterset hinzugefügt.
        return p.add(bezeichner() + "(" + id()+ ")" );
    }

    public abstract void perform();

    public abstract void addContainedObject(KochAssistentObject o);

    public abstract void removeContainedObject(KochAssistentObject o);

    public ArrayList<KochAssistentObject> getContainedObjects() {
        return storedObjects;
    }
}
