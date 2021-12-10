package aktivitaet;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.annotation.TaskAttribute;
import plan.BTAssistent;

public class LTOeffnen extends LeafTask<BTAssistent> {
    @TaskAttribute
    public String moebel;

    @Override
    public Status execute() {
        BTAssistent assi = getObject();

        System.out.println(this.getClass().getSimpleName() + ".execute()");

        if (getStatus() == Status.FRESH) {
            if (assi.getLastAction() == null) {
                System.out.println("OEFFNEN is running now ...");
                getObject().setCurrentMessage("Öffnen Sie " + moebel + "!");
                return Status.RUNNING;
            }
            else if (assi.getLastAction() instanceof Oeffnen) {
                Status s = assi.getLastAction().getStatus();
                System.out.println("OEFFNEN has already been performed: " + s);
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
                if (assi.getLastAction() instanceof Oeffnen) {
                    Status s = assi.getLastAction().getStatus();

                    System.out.println("running OEFFNEN has been completed: " + s);
                    assi.setLastAction(null);
                    return s;
                }
                else {
                    System.out.println("Although OEFFNEN is running, the user performed a different acitivity: " + assi.getLastAction().toString());
                    return Status.FAILED;
                }
            }
            else {
                System.out.println("The user did not do anything. OEFFNEN keeps running.");
                return Status.RUNNING;
            }
        }
        else return getStatus();
    }

    @Override
    protected Task copyTo(Task task) {
        return null;
    }
}
