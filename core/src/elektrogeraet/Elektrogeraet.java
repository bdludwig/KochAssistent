package elektrogeraet;

import main.KochAssistentObject;
import prolog.ParameterSet;

public class Elektrogeraet extends KochAssistentObject {
    private boolean state_on;

    public Elektrogeraet() {
        super();
        state_on = false;
    }

    public void processClick() {
        if (state_on) switchOff();
        else switchOn();
    }

    public void switchOn() {
        if (!state_on) {
            state_on = true;
        }
        else {
            // error: oven already on, and cannot be switched on (again)
        }
    }

    public void switchOff() {
        if (state_on) {
            state_on = false;
        }
        else {
            // error: oven already off, and cannot be switched off (again)
        }
    }

    public ParameterSet factsToProlog(ParameterSet p) {
        ParameterSet deviceState;

        // Klassenname des Geräts und name des Geräts werden dem Parameterset hinzugefügt.

        deviceState = p.add(bezeichner() + "(" + id() + ")" );
        if (state_on) deviceState.add("device_on(" + id() + ")");
        else deviceState.add("device_off(" + id() + ")");

        return deviceState;
    }
}
