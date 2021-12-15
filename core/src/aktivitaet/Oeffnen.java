package aktivitaet;

import com.badlogic.gdx.ai.btree.Task;
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

    @Override
    public StateDescription effects(StateDescription current) {
        ParameterSet p = current.getFacts();
        KochAssistentObject my_object = args.get(0);

        System.out.println("EFFECT now true: door_open(" + my_object.id() + ",s0)");
        System.out.println("EFFECT now false: door_closed(" + my_object.id() + ",s0)");

        p.add("door_open(" + my_object.id() + ",s0)");
        p.remove("door_closed(" + my_object.id() + ",s0)");

        return new StateDescription(p);
    }

    @Override
    public String toString() {
        return "oeffnen(" + args.get(0).id() + ")";
    }
}
