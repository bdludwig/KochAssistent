package moebel;

import main.KochAssistentObject;
import prolog.ParameterSet;

import java.util.ArrayList;

public class Schrank extends Moebel {
    protected boolean door_open;
    protected ArrayList<KochAssistentObject> storedObjects;

    public Schrank() {
        door_open = false;
        storedObjects = new ArrayList<KochAssistentObject>();
    }

    public void open() {
        if (!door_open) {
            door_open = true;
            System.out.println("door open");
        }
        else {
            // error: door already apen, and cannot be opened (again)
            System.out.println("cannot open door");

        }
    }

    public void close() {
        if (door_open) {
            door_open = false;
        }
        else {
            // error: door already closed, and cannot be closed (again)
        }
    }

    public void perform() {
        if (door_open) close();
        else open();
    }

    public boolean isOpen() {
        return door_open;
    }

    public ParameterSet factsToProlog(ParameterSet p) {
        ParameterSet deviceState = p;

        // Klassenname des Geräts und name des Geräts werden dem Parameterset hinzugefügt.
        deviceState = p.add(this.getClass().getSimpleName().toLowerCase()+ "(" + id() + ")" );
        if (door_open) deviceState.add("door_open(" + id() + ")");
        else deviceState.add("door_closed(" + id() + ")");

        for (KochAssistentObject k: storedObjects) {
            deviceState.add("contains(" + id() + "," + k.bezeichner() +")");
        }

        return deviceState;
    }
}
