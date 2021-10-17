package graph;

import com.badlogic.gdx.ai.btree.Task;
import plan.AgendaItem;
import status.StateDescription;

public class BTSelectorNode extends BTNode {
    private int current_child;

    public BTSelectorNode(int id, Task t) {
        super(id, t);
    }

    public boolean subtaskAdmissible(StateDescription current) {
        return true;
    }

    public StateDescription computeEffects(StateDescription current) {
        return current;
    }

    public void nextStep(AgendaItem ai, ALGOEdge [] succ, DijkstraGraph g) {
        AgendaItem a;

        if (current_child < succ.length) {
            ALGONode child;

            if (g.getNode(succ[current_child].getSink()) instanceof BTTreeNode) current_child++;

            System.out.println("BTSelectorNode id: " + getNodeID() + " with current child: " + current_child);
            child = g.getNode(succ[current_child].getSink());
            a = g.getTaskStatus(child.getNodeID());

            if (a != null) {
                System.out.println("BTSelectorNode status of current_child: " + a.getTaskState());

                if (a.getTaskState() == Task.Status.FAILED ||
                        a.getTaskState() == Task.Status.CANCELLED) {
                    current_child++;

                    AgendaItem a_new = new AgendaItem(a,
                            this,
                            ai.getState(),
                            Task.Status.RUNNING,
                            ai.f() + 1,
                            0);

                    g.updateTaskStatus(this.getNodeID(), a_new);
                    ALGOGraphFactory.log_stream.println(a + " -> " + a_new+ ";");
                } else if (a.getTaskState() == Task.Status.RUNNING) {
                    g.addToAgenda(a = new AgendaItem(ai, child,
                            ai.getState(),
                            a.getTaskState(),
                            ai.f() + 2,
                            0));
                    g.updateTaskStatus(child.getNodeID(), a);
                    ALGOGraphFactory.log_stream.println(ai + " -> " + a+ ";");

                    AgendaItem a_new = new AgendaItem(a,
                            this,
                            null,
                            Task.Status.RUNNING,
                            ai.f() + 1,
                            0);

                    g.updateTaskStatus(this.getNodeID(), a_new);
                    g.addToAgenda(a_new);
                    ALGOGraphFactory.log_stream.println(a + " -> " + a_new+ ";");
                } else {
                    // a.getTaskState() == Task.Status.SUCCEEDED

                    current_child++;
                    AgendaItem a_new = new AgendaItem(a,
                            this,
                            a.getState(),
                            Task.Status.RUNNING,
                            ai.f() + 1,
                            0);

                    g.updateTaskStatus(this.getNodeID(), a_new);
                    g.addToAgenda(a_new);

                    ALGOGraphFactory.log_stream.println(a + " -> " + a_new+ " [label=\"SUCCEEDED: " + this.getMyTask().getClass().getSimpleName() + " " + this.getNodeID() + "\"];");
                    System.out.println("BTSelectorNode: child succeeded. pointing to successor #: " + current_child);
                }
            }
            else {
                System.out.println("BTSelectorNode adding FRESH task to agenda: " + child.getNodeID());

                g.addToAgenda(a = new AgendaItem(ai, child,
                        ai.getState(),
                        Task.Status.FRESH,
                        ai.f() + 2,
                        0));
                g.updateTaskStatus(child.getNodeID(), a);
                ALGOGraphFactory.log_stream.println(ai + " -> " + a + " [label=\"FRESH: " + ((BTNode)child).getMyTask().getClass().getSimpleName() + " " + child.getNodeID() + "\"];");

                AgendaItem a_new = new AgendaItem(a,
                        this,
                        ai.getState(),
                        Task.Status.RUNNING,
                        ai.f() + 1,
                        0);

                g.updateTaskStatus(this.getNodeID(), a_new);
                g.addToAgenda(a_new);
                ALGOGraphFactory.log_stream.println(a + " -> " + a_new+ " [label=\"RUNNING: " + this.getMyTask().getClass().getSimpleName() + " " + this.getNodeID() + "\"];");
            }
        }
        else {
            // all tasks of this sequence succeeded

            a = new AgendaItem(ai,
                    this,
                    ai.getState(),
                    Task.Status.SUCCEEDED,
                    ai.f() + 1,
                    0);

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
