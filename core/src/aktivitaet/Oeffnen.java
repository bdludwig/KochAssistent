package aktivitaet;

import com.badlogic.gdx.ai.btree.Task;
import de.ur.ai.Renderer;
import main.KochAssistentObject;
import moebel.Schrank;
import prolog.ParameterSet;
import prolog.Substitution;
import status.StateDescription;

import java.util.List;

public class Oeffnen extends Aktivitaet {
    public Oeffnen() {
        super();
    }

    @Override
    public Substitution effects_satisfied(ParameterSet current_sit) {
        ParameterSet p = new ParameterSet();

        p.add("door_open(" + args.get(0).id() + ",s0)");
        return new StateDescription(current_sit).entails(p);
    }

    @Override
    public boolean isPossible(ParameterSet current_sit) {
        KochAssistentObject my_object = args.get(0);
        StateDescription s = new StateDescription(current_sit);

        if (my_object instanceof Schrank) {
            return s.entails(new ParameterSet("poss(oeffnen(" + my_object.id() + "),s0)")) != null;
        }
        else {
            System.out.println("not a Schrank");
            return false;
        }
    }

    @Override
    public Task.Status perform() {
        KochAssistentObject my_object = args.get(0);

        if (my_object instanceof Schrank) {
            ((Schrank)my_object).open();
            return Task.Status.SUCCEEDED;
        }
        else {
            System.out.println("not a Schrank");
            return Task.Status.FAILED;
        }
    }
}
