package plan;

import com.badlogic.gdx.ai.btree.Task;
import graph.ALGONode;
import status.ActionTerm;
import status.StateDescription;

public class AgendaItem implements Comparable {
    private AgendaItem pred;
    private ALGONode tree_node;
    private StateDescription state;
    private double actual_cost;
    private double estimated_cost_to_goal;
    private Task.Status task_state;
    private int id = -1;

    public AgendaItem(ALGONode t, StateDescription s, Task.Status ts, double h) {
        actual_cost = 0;
        estimated_cost_to_goal = h;
        pred = null;
        state = s;
        tree_node = t;
        task_state = ts;
    }

    public void setID(int i) {
        id = i;
    }
    public AgendaItem(AgendaItem p, ALGONode t, StateDescription s, Task.Status ts, double c, double h) {
        actual_cost = c;
        estimated_cost_to_goal = h;
        pred = p;
        tree_node = t;
        state = s;
        task_state = ts;
    }

    public AgendaItem getPred() {
        return pred;
    }

    public StateDescription getState() {
        return state;
    }

    public ALGONode getNode() {
        return tree_node;
    }

    public Task.Status getTaskState() {
        return task_state;
    }

    public void setState(StateDescription s) {
        state = s;
    }

    public double g() {
        return actual_cost;
    }

    public double h() {
        return estimated_cost_to_goal;
    }

    public double f() {
        return g() + h();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof AgendaItem) {
            return (int)(this.f() - ((AgendaItem)o).f());
        }
        return 0;
    }

    public String toString() {
        return "item_" + id;
    }
}
