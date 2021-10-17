package graph;

import com.badlogic.gdx.ai.btree.Task;
import plan.AgendaItem;
import status.StateDescription;
import zubereitung.Zubereitung;

public class BTTreeNode extends BTNode {
    public BTTreeNode(int id, Task t) {
        super(id, t);
    }

    public boolean subtaskAdmissible(StateDescription current) {
        if (myTask instanceof Zubereitung) return ((Zubereitung)myTask).preconditionsSatisfied(current);
        else {
            System.out.println("illegal task type: "+ myTask.getClass().getSimpleName());
            return false;
        }
    }

    public void nextStep(AgendaItem ai, ALGOEdge [] succ, DijkstraGraph g) {
        System.out.println(((BTNode)ai.getNode()).getMyTask().getChild(0));

        AgendaItem a = new AgendaItem(g.getNode(succ[0].getSink()),
                ai.getState(),
                Task.Status.RUNNING,
                0);

        g.updateTaskStatus(a.getNode().getNodeID(), a);
        g.addToAgenda(a);
        ALGOGraphFactory.log_stream.println("root -> " + a+ ";");
    }

    public StateDescription computeEffects(StateDescription current) {
        if (myTask instanceof Zubereitung) return ((Zubereitung)myTask).effects(current);
        else {
            System.out.println("illegal task type: "+ myTask.getClass().getSimpleName());
            return current;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else if (!(o instanceof BTTreeNode)) return false;
        else return myTask.equals(((BTTreeNode)o).getMyTask());
    }
}
