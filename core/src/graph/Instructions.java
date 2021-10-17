package graph;

import com.badlogic.gdx.ai.btree.Task;

import java.util.LinkedList;

public class Instructions extends LinkedList<Task> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int currentTripStep;

    public Instructions() {
        super();
        currentTripStep = 0;
    }

    public void appendPart(Task e) {
        this.add(0, e);
    }

    public void reset() {
        currentTripStep = 0;
    }

    public Task getNextStep() {
        if (currentTripStep < this.size()) return get(currentTripStep ++);
        else return null;
    }
}