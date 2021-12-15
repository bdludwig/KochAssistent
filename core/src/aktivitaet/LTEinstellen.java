package aktivitaet;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import plan.BTAssistent;
import prolog.ParameterSet;
import prolog.Substitution;
import status.StateDescription;

public class LTEinstellen extends LeafTask<BTAssistent> {
    @TaskAttribute
    public String moebel;
    @TaskAttribute
    public String objekt;

    @Override
    public Status execute() {
        BTAssistent assi = getObject();

        System.out.println(this.getClass().getSimpleName() + ".execute()");

        if (getStatus() == Status.FRESH) {
            if (effects_satisfied(assi.currentSituation()) == null) {
                System.out.println("EINSTELLEN is running now ...");
                getObject().setCurrentMessage("Stellen Sie " + objekt + " in " + moebel + "!");
                return Status.RUNNING;
            }
            else {
                System.out.println("Effects of EINSTELLEN already satisfied.");
                return Status.SUCCEEDED;
            }
        }
        else if (getStatus() == Status.RUNNING) {
            if (effects_satisfied(assi.currentSituation()) != null) {
                System.out.println("running EINSTELLEN has been completed.");
                return Status.SUCCEEDED;
            }
            else {
                System.out.println("Although EINSTELLEN is running, the user performed some other activity.");
                return Status.RUNNING;
            }
        }
        else {
            System.out.println("no operation. Current state: " + getStatus());
            return getStatus();
        }
    }

    @Override
    protected Task<BTAssistent> copyTo(Task<BTAssistent> task) {
        return null;
    }

    private Substitution effects_satisfied(StateDescription current_sit) {
        ParameterSet p = new ParameterSet();

        p.add("contains(" + moebel + "," + objekt + ",s0)");

        return current_sit.entails(p);
    }
}
