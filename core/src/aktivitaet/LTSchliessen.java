package aktivitaet;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import plan.BTAssistent;

public class LTSchliessen extends LeafTask<BTAssistent> {
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
                System.out.println("SCHLIESSEN is running now ...");
                getObject().setCurrentMessage("Schließen Sie Schrank " + moebel + "!");
                return Status.RUNNING;
            }
            else if (assi.getLastAction() instanceof Schliessen) {
                Status s = assi.getLastAction().getStatus();
                System.out.println("SCHLIESSEN has already been performed: " + assi.getLastAction().getStatus());
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
                if (assi.getLastAction() instanceof Schliessen) {
                    System.out.println("running SCHLIESSEN has been completed: " + assi.getLastAction().getStatus());
                    return assi.getLastAction().getStatus();
                }
                else {
                    System.out.println("Although SCHLIESSEN is running, the user performed a different acitivity: " + assi.getLastAction().toString());
                    return Status.FAILED;
                }
            }
            else {
                System.out.println("The user did not do anything. SCHLIESSEN keeps running.");
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