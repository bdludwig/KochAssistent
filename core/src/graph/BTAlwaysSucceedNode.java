package graph;

import com.badlogic.gdx.ai.btree.Task;
import plan.AgendaItem;
import status.StateDescription;

public class BTAlwaysSucceedNode extends BTNode {
    private int current_child;

    public BTAlwaysSucceedNode(int id, Task t) {
        super(id, t);
        current_child = 0;
    }

    public boolean subtaskAdmissible(StateDescription current) {
        return true;
    }

    public StateDescription computeEffects(StateDescription current) {
        return current;
    }

    public void nextStep(AgendaItem ai, ALGOEdge [] succ, DijkstraGraph g) {
        ALGONode child;
        AgendaItem a;

        if (g.getNode(succ[current_child].getSink()) instanceof BTTreeNode) current_child++;

        if (current_child < succ.length) {
            System.out.println("BTAlwaysSucceedNode id: " + getNodeID() + " with current child: " + current_child);
            child = g.getNode(succ[current_child].getSink());
            System.out.println("BTAlwaysSucceedNode current_child: " + child.getNodeID());

            a = g.getTaskStatus(child.getNodeID());

            if (a != null) {
                System.out.println("BTAlwaysSucceedNode status of current_child: " + a.getTaskState());

                current_child = 0;
                a = new AgendaItem(ai,
                        this,
                        ai.getState(),
                        Task.Status.SUCCEEDED,
                        ai.f() + 1,
                        0);

                g.updateTaskStatus(this.getNodeID(), a);
                ALGOGraphFactory.log_stream.println(ai + " -> " + a + " [label=\"SUCCEEDED: " + this.getMyTask().getClass().getSimpleName() + " " + this.getNodeID() + "\"]");
            }
            else {
                System.out.println("BTAlwaysSucceedNode adding FRESH task to agenda: " + child.getNodeID());

                g.addToAgenda(a = new AgendaItem(ai, child,
                        ai.getState(),
                        Task.Status.FRESH,
                        ai.f() + 2,
                        0));
                g.updateTaskStatus(child.getNodeID(), a);
                ALGOGraphFactory.log_stream.println(ai + " -> " + a + " [label=\"FRESH: " + ((BTNode)child).getMyTask().getClass().getSimpleName() + " " + child.getNodeID() + "\"]");

                AgendaItem a_new = new AgendaItem(a,
                        this,
                        ai.getState(),
                        Task.Status.RUNNING,
                        ai.f() + 1,
                        0);

                g.updateTaskStatus(this.getNodeID(), a_new);
                g.addToAgenda(a_new);
                ALGOGraphFactory.log_stream.println(a + " -> " + a_new + " [label=\"RUNNING: " + this.getMyTask().getClass().getSimpleName() + " " + this.getNodeID() + "\"]");
            }
        }
        else {
            current_child = 0;
            a = new AgendaItem(ai,
                    this,
                    ai.getState(),
                    Task.Status.SUCCEEDED,
                    ai.f() + 1,
                    0);

            ALGOGraphFactory.log_stream.println(ai + " -> " + a + " [label=\"SUCCEEDED: " + this.getMyTask().getClass().getSimpleName() + " " + this.getNodeID() + "\"]");

            g.updateTaskStatus(this.getNodeID(), a);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else if (!(o instanceof BTSelectorNode)) return false;
        else return myTask.equals(((BTSelectorNode)o).getMyTask());
    }
}
