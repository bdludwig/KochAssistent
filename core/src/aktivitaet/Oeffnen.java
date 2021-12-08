package aktivitaet;

import de.ur.ai.Renderer;
import main.KochAssistentObject;
import moebel.Schrank;
import prolog.ParameterSet;
import status.StateDescription;

import java.util.List;

public class Oeffnen extends Aktivitaet {
    public Oeffnen() {
        super();
    }

    @Override
    public boolean isPossible(List<Renderer> objects, ParameterSet current_sit) {
        Renderer my_object = objects.get(0);
        StateDescription s = new StateDescription(current_sit);

        if (my_object.getObject() instanceof Schrank) {
            //System.out.println(s.entails(new ParameterSet("poss(oeffnen(" + my_object.getObject().id() + "),s0)")));

            System.out.println(s.entails(new ParameterSet("do(oeffnen(o_9):entnehmen(O,o_9),s0,S)")));

            return s.entails(new ParameterSet("poss(oeffnen(" + my_object.getObject().id() + "),s0)")) != null;
        }
        else {
            System.out.println("not a Schrank");
            return false;
        }
    }

    @Override
    public void perform(List<Renderer> objects) {
        Renderer my_object = objects.get(0);

        if (my_object.getObject() instanceof Schrank) {
            ((Schrank)my_object.getObject()).open();
        }
        else System.out.println("not a Schrank");
    }
}
