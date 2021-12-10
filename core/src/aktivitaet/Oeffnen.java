package aktivitaet;

import com.badlogic.gdx.ai.btree.Task;
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
            return s.entails(new ParameterSet("poss(oeffnen(" + my_object.getObject().id() + "),s0)")) != null;
        }
        else {
            System.out.println("not a Schrank");
            return false;
        }
    }

    @Override
    public Task.Status perform(List<Renderer> objects) {
        Renderer my_object = objects.get(0);

        if (my_object.getObject() instanceof Schrank) {
            ((Schrank)my_object.getObject()).open();
            return Task.Status.SUCCEEDED;
        }
        else {
            System.out.println("not a Schrank");
            return Task.Status.FAILED;
        }
    }
}
