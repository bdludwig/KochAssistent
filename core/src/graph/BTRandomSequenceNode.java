package graph;

import com.badlogic.gdx.ai.btree.Task;
import plan.AgendaItem;
import status.StateDescription;

import java.util.Random;

public class BTRandomSequenceNode extends BTNode {
    private int current_child;
    private Task.Status[] child_status;
    private Random r;

    public BTRandomSequenceNode(int id, Task t) {

        super(id, t);

        child_status = new Task.Status[this.getMyTask().getChildCount() + 1];
        for (int i = 0; i < child_status.length; i++) child_status[i] = Task.Status.FRESH;

        r = new Random();
        current_child = r.nextInt(child_status.length);
    }

    private int nextChildToTick(ALGOEdge [] succ, DijkstraGraph g, int succlength) {
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

        if (best_child == -1) return r.nextInt(succ.length);
        else return best_child;
    }


    public boolean subtaskAdmissible(StateDescription current) {
        return true;
    }

    public StateDescription computeEffects(StateDescription current) {
        return current;
    }

    public void nextStep(AgendaItem ai, ALGOEdge [] succ, DijkstraGraph g) {
        AgendaItem a;
        ALGONode child;

        if (g.getNode(succ[current_child].getSink()) instanceof BTTreeNode) current_child++;

        System.out.println("BTRandomSequenceNode id: " + getNodeID() + " with current child: " + current_child);
        child = g.getNode(succ[current_child].getSink());
        a = g.getTaskStatus(child.getNodeID());


        if (a != null) {
            child_status[current_child] = a.getTaskState();
            System.out.println("BTRandomSequenceNode status of current_child: " + a.getTaskState());

            if (a.getTaskState() == Task.Status.FAILED ||
                    a.getTaskState() == Task.Status.CANCELLED) {
                AgendaItem a_new = new AgendaItem(a,
                        this,
                        ai.getState(),
                        a.getTaskState(),
                        ai.f() + 2,
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
                // randomly select the next task in this sequence

                boolean random_sequence_successful = true;
                int i = 0;

                while (i < succ.length) {
                    if (!(g.getNode(succ[i].getSink()) instanceof BTTreeNode) &&
                            child_status[i] != Task.Status.SUCCEEDED) {
                        random_sequence_successful = false;
                        break;
                    } else i++;
                }

                if (random_sequence_successful) {
                    // all tasks of this sequence succeeded

                    a = new AgendaItem(ai,
                            this,
                                ai.getState(),
                                Task.Status.SUCCEEDED,
                                ai.f() + 1,
                                0);

                    g.updateTaskStatus(this.getNodeID(), a);
                    ALGOGraphFactory.log_stream.println(ai + " -> " + a + " [label=\"SUCCEEDED: " + this.getMyTask().getClass().getSimpleName() + " " + this.getNodeID() + "\"];");
                } else {
                    do {
                        current_child = nextChildToTick(succ, g, succ.length);
                        System.out.println("BTRandomSequnceNode: testing as next child: " + current_child + " " + child_status[current_child]);
                        if (g.getNode(succ[current_child].getSink()) instanceof BTTreeNode) current_child++;
                    }
                    while (!(child_status[current_child] == Task.Status.RUNNING ||
                            child_status[current_child] == Task.Status.FRESH));

                    System.out.println("BTRandomSequnceNode: choosing as next child: " + current_child);
                    AgendaItem a_new = new AgendaItem(a,
                                this,
                                a.getState(),
                                Task.Status.RUNNING,
                                ai.f() + 1,
                                0);

                    g.updateTaskStatus(this.getNodeID(), a_new);
                    g.addToAgenda(a_new);
                    ALGOGraphFactory.log_stream.println(a + " -> " + a_new+ " [label=\"RUNNING: " + this.getMyTask().getClass().getSimpleName() + " " + this.getNodeID() + "\"];");

                    System.out.println("BTSequenceNode: child succeeded. pointing to successor #: " + current_child);
                }
            }
        }
        else {
            System.out.println("BTSequenceNode adding FRESH task to agenda: " + child.getNodeID());
            child_status[current_child] = Task.Status.FRESH;

            g.addToAgenda(a = new AgendaItem(ai, child,
                        ai.getState(),
                        Task.Status.FRESH,
                        ai.f() + 2,
                        0));
            g.updateTaskStatus(child.getNodeID(), a);
            ALGOGraphFactory.log_stream.println(ai + " -> " + a+ " [label=\"FRESH: " + ((BTNode)child).getMyTask().getClass().getSimpleName() + " " + child.getNodeID() + "\"];");

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

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        else if (!(o instanceof BTRandomSequenceNode)) return false;
        else return myTask.equals(((BTRandomSequenceNode)o).getMyTask());
    }
}
