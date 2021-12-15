package aktivitaet;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import plan.BTAssistent;

public class LTEinstellen extends LeafTask<BTAssistent> {
    @TaskAttribute
    public String moebel;
    @TaskAttribute
    public String objekt;

    @Override
    public Status execute() {
        System.out.println(this.getClass().getSimpleName() + ".execute()");

        if (getStatus() == Status.FRESH) {
            getObject().setCurrentMessage("Stellen Sie " + objekt + " in " + moebel + "!");

            return Status.RUNNING;
        }
        else if (getStatus() == Status.RUNNING) {
            BTAssistent assi = getObject();
            /*
            if (assi.getLastAction() != null) {
                if (assi.getLastAction() instanceof Einstellen) return assi.getLastAction().getStatus();
                else return Status.FAILED;
            }
            else return Status.RUNNING;

             */

            return Status.SUCCEEDED;
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
}
