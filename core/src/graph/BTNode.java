package graph;

import com.badlogic.gdx.ai.btree.Task;
import plan.AgendaItem;
import status.StateDescription;

public abstract class BTNode extends ALGONode {
    protected Task myTask;

    public BTNode(int id, Task t) {
        super(id);

        myTask = t;
    }

    abstract public void nextStep(AgendaItem ai, ALGOEdge [] succ, DijkstraGraph g);

    abstract public boolean subtaskAdmissible(StateDescription current);

    abstract public StateDescription computeEffects(StateDescription current);

    public Task getMyTask() {
        return myTask;
    }

    @Override
    public String toString() {
        return nodeID + ": " + myTask.getClass().getSimpleName();
    }

    @Override
    public double costs(ALGONode targetNode) {
        return 0;
    }

    @Override
    public String toGraphViz() {
        return null;
    }
}
