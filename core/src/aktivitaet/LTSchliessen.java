package aktivitaet;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import moebel.Schrank;
import plan.BTAssistent;
import prolog.ParameterSet;
import prolog.Substitution;
import status.StateDescription;

public class LTSchliessen extends LeafTask<BTAssistent> {
    @TaskAttribute
    public String moebel;

    @Override
    public Status execute() {
        BTAssistent assi = getObject();

        System.out.println(this.getClass().getSimpleName() + ".execute()");

        if (getStatus() == Status.FRESH) {
            if (effects_satisfied(assi.currentSituation()) == null) {
                System.out.println("SCHLIESSEN is running now ...");
                getObject().setCurrentMessage("Schließen Sie Schrank " + moebel + "!");
                return Status.RUNNING;
            }
            else {
                System.out.println("Effects of SCHLIESSEN already safisfied.");
                return Status.SUCCEEDED;
            }
        }
        else if (getStatus() == Status.RUNNING) {
            if (effects_satisfied(assi.currentSituation()) != null) {
                System.out.println("running SCHLIESSEN has been completed.");
                return Status.SUCCEEDED;
            }
            else {
                System.out.println("Although SCHLIESSEN is running, the user performed some other activity.");
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

    public Substitution effects_satisfied(StateDescription current_sit) {
        ParameterSet effects = new ParameterSet();

        System.out.println("SCHLIESSEN expected effect: door_closed(" + moebel + ",s0)");
        effects.add("door_closed(" + moebel + ",s0)");

        return current_sit.entails(effects);
    }
}