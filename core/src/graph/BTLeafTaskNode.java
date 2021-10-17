package graph;

import com.badlogic.gdx.ai.btree.Task;
import plan.AgendaItem;
import status.StateDescription;
import zubereitung.Zubereitung;

public class BTLeafTaskNode extends BTNode {
    public BTLeafTaskNode(int id, Task t) {
        super(id, t);
    }

    @Override
    public void nextStep(AgendaItem ai, ALGOEdge [] succ, DijkstraGraph g) {
        Task t = getMyTask();

        if (t instanceof Zubereitung) {
            if (((Zubereitung)t).preconditionsSatisfied(ai.getState())) {
                StateDescription new_state = ((Zubereitung) t).effects(ai.getState());
                System.out.println(t.getClass().getSimpleName() + " has state: " + ((Zubereitung) t).currentTaskState(new_state));

                System.out.println(new_state.getFacts());
                AgendaItem a = new AgendaItem(ai,
                        this,
                        new_state,
                        ((Zubereitung) t).currentTaskState(new_state),
                        ai.f(),
                        0);

                g.updateTaskStatus(this.getNodeID(), a);
                ALGOGraphFactory.log_stream.println(ai + " -> " + a + " [label=\"EFFECTS: " + this.getMyTask().getClass().getSimpleName() + " " + this.getNodeID() + "\"];");
            }
            else {
                System.out.println(t.getClass().getSimpleName() + " has unsatisfied preconditions.");

                // a running task with unsatisfied preconditions has to be ticked again.

                AgendaItem a = new AgendaItem(ai,
                        this,
                        ai.getState(),
                        Task.Status.RUNNING,
                        ai.f(),
                        0);

                g.updateTaskStatus(this.getNodeID(), a);
                ALGOGraphFactory.log_stream.println(ai + " -> " + a + ";");
            }
        }
        else {
            System.out.println("not an instanceof of Zubereitung");
        }
    }

    @Override
    public String toString() {
        return nodeID + ": " + myTask.getClass().getSimpleName();
    }

    @Override
    public String toGraphViz() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else if (!(o instanceof BTLeafTaskNode)) return false;
        else return myTask.equals(((BTLeafTaskNode)o).getMyTask());
    }

    @Override
    public double costs(ALGONode targetNode) {
        return 1;
    }

    public boolean subtaskAdmissible(StateDescription current) {
        if (myTask instanceof Zubereitung) return ((Zubereitung)myTask).preconditionsSatisfied(current);
        else {
            System.out.println("illegal task type: "+ myTask.getClass().getSimpleName());
            return false;
        }
    }

    public StateDescription computeEffects(StateDescription current) {
        if (myTask instanceof Zubereitung) return ((Zubereitung)myTask).effects(current);
        else {
            System.out.println("illegal task type: "+ myTask.getClass().getSimpleName());
            return current;
        }
    }
}
