package aktivitaet;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import plan.BTAssistent;
import prolog.ParameterSet;
import prolog.Substitution;
import status.StateDescription;

public class LTOeffnen extends LeafTask<BTAssistent> {
    @TaskAttribute
    public String moebel;

    @Override
    public Status execute() {
        BTAssistent assi = getObject();

        System.out.println(this.getClass().getSimpleName() + ".execute: " + assi.getLastAction());

        if (getStatus() == Status.FRESH) {
            if (effects_satisfied(assi.currentSituation()) == null) {
                System.out.println("OEFFNEN is running now ...");
                getObject().setCurrentMessage("Öffnen Sie " + moebel + "!");
                return Status.RUNNING;
            }
            else {
                System.out.println("Effects of OEFFNEN already safisfied.");
                return Status.SUCCEEDED;
            }
        }
        else if (getStatus() == Status.RUNNING) {
            if (effects_satisfied(assi.currentSituation()) != null) {
                System.out.println("running OEFFNEN has been completed.");
                return Status.SUCCEEDED;
            }
            else {
                System.out.println("Although OEFFNEN is running, the user performed some other activity.");
                return Status.RUNNING;
            }
        }
        else {
            System.out.println("no operation. Current state: " + getStatus());
            return getStatus();
        }
    }

    @Override
    protected Task copyTo(Task task) {
        return null;
    }

    private Substitution effects_satisfied(StateDescription current_sit) {
        ParameterSet p = new ParameterSet();

        p.add("door_open(" + moebel + ",s0)");
        return current_sit.entails(p);
    }
}
