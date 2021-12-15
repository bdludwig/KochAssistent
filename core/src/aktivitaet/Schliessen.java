package aktivitaet;

import com.badlogic.gdx.ai.btree.Task;
import de.ur.ai.Renderer;
import main.KochAssistentObject;
import moebel.Schrank;
import prolog.ParameterSet;
import prolog.Substitution;
import status.StateDescription;

import java.util.List;

public class Schliessen extends Aktivitaet {
    public Schliessen() {
        super();
    }

    @Override
    public boolean isPossible(ParameterSet current_sit) {
        KochAssistentObject my_object = args.get(0);

        if (my_object instanceof Schrank) {
            if (((Schrank)my_object).isOpen()) return true;
            else return false;
        }
        else {
            System.out.println("not a Schrank");
            return false;
        }
    }

    @Override
    public Substitution effects_satisfied(ParameterSet current_sit) {
        ParameterSet effects = new ParameterSet();
        Schrank obj = (Schrank)args.get(0);

        effects.add("door_closed(" + obj.id() + ",s0)");

        return new StateDescription(current_sit).entails(effects);
    }

    @Override
    public Task.Status perform() {
        KochAssistentObject my_object = args.get(0);

        if (my_object instanceof Schrank) {
            ((Schrank)my_object).close();

            return Task.Status.SUCCEEDED;
        }
        else {
            System.out.println("not a Schrank");
            return Task.Status.FAILED;
        }
    }
}
