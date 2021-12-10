package aktivitaet;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import plan.BTAssistent;

public class LTAbstellen extends LeafTask<BTAssistent> {
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
                System.out.println("ABSTELLEN is running now ...");
                getObject().setCurrentMessage("Stellen Sie " + objekt + " auf " + moebel + "!");
                return Status.RUNNING;
            }
            else if (assi.getLastAction() instanceof Abstellen) {
                Status s = assi.getLastAction().getStatus();
                System.out.println("ABSTELLEN has already been performed: " + s);
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
                if (assi.getLastAction() instanceof Abstellen) {
                    System.out.println("running ABSTELLEN has been completed: " + assi.getLastAction().getStatus());
                    return assi.getLastAction().getStatus();
                }
                else {
                    System.out.println("Although ABSTELLEN is running, the user performed a different acitivity: " + assi.getLastAction().toString());
                    return Status.FAILED;
                }
            }
            else {
                System.out.println("The user did not do anything. ABSTELLEN keeps running.");
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
