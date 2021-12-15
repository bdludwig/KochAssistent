package moebel;

import main.KochAssistentObject;
import prolog.ParameterSet;

public class Arbeitsplatte extends Moebel {
    public Arbeitsplatte() {
        super();
    }

    @Override
    public ParameterSet factsToProlog(ParameterSet p) {
        ParameterSet deviceState = p;

        // Klassenname des Geräts und name des Geräts werden dem Parameterset hinzugefügt.
        deviceState = p.add(bezeichner() + "(" + id() + ")");

        for (KochAssistentObject k : storedObjects) {
            deviceState.add("contains(" + id() + "," + k.id() + ",s0" + ")");
        }

        return deviceState;
    }

    @Override
    public void perform() {

    }

    @Override
    public void addContainedObject(KochAssistentObject o) {
        storedObjects.add(o);
    }

    @Override
    public void removeContainedObject(KochAssistentObject o) {
        System.out.println(this.getClass().getSimpleName() + ": " + storedObjects.size());
        storedObjects.remove(o);
        System.out.println(this.getClass().getSimpleName() + ": " + storedObjects.size());
    }
}
