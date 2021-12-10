package aktivitaet;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import plan.BTAssistent;

public class LTHerausnehmen extends LeafTask<BTAssistent> {
    @TaskAttribute
    public String moebel;
    @TaskAttribute
    public String objekt;

    @Override
    public Status execute() {
        BTAssistent assi = getObject();

        System.out.println(this.getClass().getSimpleName() + ".execute()");

        if (getStatus() == Status.FRESH) {
            if (assi.getLastAction() == null) {
                System.out.println("HERAUSNEHMEN is running now ...");
                getObject().setCurrentMessage("Schließen Sie Schrank " + moebel + "!");
                return Status.RUNNING;
            }
            else if (assi.getLastAction() instanceof Herausnehmen) {
                Status s = assi.getLastAction().getStatus();
                System.out.println("HERAUSNEHMEN has already been performed: " + assi.getLastAction().getStatus());
                assi.setLastAction(null);
                return s;
            }
            else {
                System.out.println("The user performed a different activity: " + assi.getLastAction().toString());
                return Status.FAILED;
            }
        }
        else if (getStatus() == Status.RUNNING) {
            if (assi.getLastAction() != null) {
                if (assi.getLastAction() instanceof Herausnehmen) {
                    System.out.println("running HERAUSNEHMEN has been completed: " + assi.getLastAction().getStatus());
                    return assi.getLastAction().getStatus();
                }
                else {
                    System.out.println("Although HERAUSNEHMEN is running, the user performed a different acitivity: " + assi.getLastAction().toString());
                    return Status.FAILED;
                }
            }
            else {
                System.out.println("The user did not do anything. HERAUSNEHMEN keeps running.");
                return getStatus();
            }
        }
        else return getStatus();
    }

    @Override
    protected Task<BTAssistent> copyTo(Task<BTAssistent> task) {
        return null;
    }
}