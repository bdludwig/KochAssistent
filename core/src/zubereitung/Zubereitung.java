package zubereitung;

import com.badlogic.gdx.ai.btree.LeafTask;
import status.StateDescription;

public abstract class Zubereitung extends LeafTask {
    public boolean preconditionsSatisfied(StateDescription current) {
        return true;
    }

    public StateDescription effects(StateDescription current) {
        return current;
    }

    public Status currentTaskState(StateDescription current) {
        // test whether the current state description satisfies conditions that are sufficient
        // (a) for declaring the task failed
        // (b) for declaring it successful
        // () for being still running

        return Status.SUCCEEDED;
    }
}
