package moebel;

import de.ur.ai.Drop;
import main.KochAssistentObject;
import prolog.ParameterSet;

import java.util.ArrayList;

public class Schrank extends Moebel {
    protected boolean door_open;

    public Schrank() {
        door_open = false;
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
        if (door_open) {
            close();
        }
        else open();
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

    public boolean isOpen() {
        return door_open;
    }

    @Override
    public ParameterSet factsToProlog(ParameterSet p) {
        ParameterSet deviceState = p;

        // Klassenname des Geräts und name des Geräts werden dem Parameterset hinzugefügt.
        deviceState = p.add(bezeichner() + "(" + id() + ")" );
        if (door_open) deviceState.add("door_open(" + id() + ",s0" + ")");
        else deviceState.add("door_closed(" + id() + ",s0" + ")");

        for (KochAssistentObject k: storedObjects) {
            deviceState.add("contains(" + id() + "," + k.id() + ",s0" +")");
        }

        return deviceState;
    }
}
