package graph;

import com.badlogic.gdx.ai.btree.Task;
import zubereitung.Zubereitung;

import java.util.Random;

public class ALGOCookingEdge extends ALGOEdge {
    private Random r = new Random();

    public ALGOCookingEdge() {
        super();
    }

    public ALGOCookingEdge(int soid, int siid) {
        super(soid, siid);
    }

    public ALGOCookingEdge(int soid, int siid, double c) {
        super(soid, siid, c);
    }

    public double getCosts(Task sinkTask) {
        return 1.0;
    }
}
