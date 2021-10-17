package graph;

import com.badlogic.gdx.ai.btree.Task;
import plan.AgendaItem;
import status.StateDescription;

public class BTParallelNode extends BTNode {
    private int current_child;
    private Task.Status[] child_status;

    public BTParallelNode(int id, Task t) {
        super(id, t);

        current_child = 0;

        child_status = new Task.Status[this.getMyTask().getChildCount() + 1];
        for (int i = 0; i < child_status.length; i++) child_status[i] = Task.Status.FRESH;
    }

    public boolean subtaskAdmissible(StateDescription current) {
        return true;
    }

    public StateDescription computeEffects(StateDescription current) {
        return current;
    }

    private int nextChildToTick(ALGOEdge [] succ, DijkstraGraph g) {
        double max_edge_costs = Double.POSITIVE_INFINITY;
        double costs;
        int best_child = -1;
        ALGOEdge e;

        for (int i = 0; i < succ.length; i++) {
            e = succ[i];

            if (e instanceof ALGOCookingEdge) {
                if (!(g.getNode(succ[i].getSink()) instanceof BTTreeNode)) {
                    costs = ((ALGOCookingEdge)e).getCosts(((BTNode)g.getNode(e.getSink())).getMyTask());
                    if ((costs < max_edge_costs) && (child_status[i] == Task.Status.FRESH || (child_status[i] == Task.Status.RUNNING))){
                        max_edge_costs = costs;
                        best_child = i;
                    }
                }
            }
        }

        if (best_child == -1) return current_child + 1;
        else return best_child;
    }

    public void nextStep(AgendaItem ai, ALGOEdge [] succ, DijkstraGraph g) {
        AgendaItem a;

        if (current_child < succ.length) {
            ALGONode child;

            if (g.getNode(succ[current_child].getSink()) instanceof BTTreeNode) current_child++;

            System.out.println("BTParallelNode id: " + getNodeID());
            child = g.getNode(succ[current_child].getSink());
            a = g.getTaskStatus(child.getNodeID());

            if (a != null) {
                child_status[current_child] = a.getTaskState();
                System.out.println("BTParallelNode status of current_child: " + a.getTaskState());

                if (a.getTaskState() == Task.Status.FAILED ||
                        a.getTaskState() == Task.Status.CANCELLED) {
                    AgendaItem a_new = new AgendaItem(a,
                            this,
                            ai.getState(),
                            a.getTaskState(),
                            ai.f() + 1,
                            0);

                    g.updateTaskStatus(this.getNodeID(), a_new);
                    ALGOGraphFactory.log_stream.println(a + " -> " + a_new + ";");
                } else if (a.getTaskState() == Task.Status.RUNNING) {
                    current_child = nextChildToTick(succ, g);

                    g.addToAgenda(a = new AgendaItem(ai, child,
                            ai.getState(),
                            a.getTaskState(),
                            ai.f() + 2,
                            0));
                    g.updateTaskStatus(child.getNodeID(), a);
                    ALGOGraphFactory.log_stream.println(ai + " -> " + a + " [label=\"SUCCEEDED: " + ((BTNode)child).getMyTask().getClass().getSimpleName() + " " + child.getNodeID() + "\"];");

                    AgendaItem a_new = new AgendaItem(a,
                            this,
                            null,
                            Task.Status.RUNNING,
                            ai.f() + 1,
                            0);

                    g.updateTaskStatus(this.getNodeID(), a_new);
                    g.addToAgenda(a_new);
                    ALGOGraphFactory.log_stream.println(a + " -> " + a_new + " [label=\"SUCCEEDED: " + this.getMyTask().getClass().getSimpleName() + " " + this.getNodeID() + "\"];");
                } else {
                    // a.getTaskState() == Task.Status.SUCCEEDED

                    current_child = nextChildToTick(succ, g);
                    AgendaItem a_new = new AgendaItem(a,
                            this,
                            a.getState(),
                            Task.Status.RUNNING,
                            ai.f() + 1,
                            0);

                    g.updateTaskStatus(this.getNodeID(), a_new);
                    g.addToAgenda(a_new);
                    ALGOGraphFactory.log_stream.println(a + " -> " + a_new + " [label=\"RUNNING: " + this.getMyTask().getClass().getSimpleName() + " " + this.getNodeID() + "\"];");
                }
            }
            else {
                System.out.println("BTParallelNode adding FRESH child to agenda: " + child.getNodeID());

                g.addToAgenda(a = new AgendaItem(ai, child,
                        ai.getState(),
                        Task.Status.FRESH,
                        ai.f() + 2,
                        0));
                g.updateTaskStatus(child.getNodeID(), a);
                ALGOGraphFactory.log_stream.println(ai + " -> " + a + " [label=\"FRESH: " + ((BTNode)child).getMyTask().getClass().getSimpleName() + " " + child.getNodeID() + "\"];");

                AgendaItem a_new = new AgendaItem(a,
                        this,
                        null,
                        Task.Status.RUNNING,
                        ai.f() + 1,
                        0);

                g.updateTaskStatus(this.getNodeID(), a_new);
                g.addToAgenda(a_new);
                ALGOGraphFactory.log_stream.println(a + " -> " + a_new + " [label=\"RUNNING: " + this.getMyTask().getClass().getSimpleName() + " " + this.getNodeID() + "\"];");
            }
        }
        else {
            // all tasks of this sequence excuted for one tick

            current_child = 0;

            a = new AgendaItem(ai,
                    this,
                    ai.getState(),
                    Task.Status.SUCCEEDED,
                    ai.g() + 1,
                    0);

            g.updateTaskStatus(this.getNodeID(), a);
            ALGOGraphFactory.log_stream.println(ai + " -> " + a+ " [label=\"SUCCEEDED: " + this.getMyTask().getClass().getSimpleName() + " " + this.getNodeID() + "\"];");
        }
   }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else if (!(o instanceof BTParallelNode)) return false;
        else return myTask.equals(((BTParallelNode)o).getMyTask());
    }
}
